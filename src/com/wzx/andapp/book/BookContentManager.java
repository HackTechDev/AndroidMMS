package com.wzx.andapp.book;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wzx.andapp.shh.R;

public class BookContentManager implements IAdapter {

	private File book_file = null;
	private MappedByteBuffer m_mbBuf = null;
	private int m_mbBufLen = 0;
	private int m_mbBufBegin = 0;
	private int m_mbBufEnd = 0;
	private String m_strCharsetName = "GBK";
	private int mWidth;
	private int mHeight;

	private ArrayList<ArrayList<String>> pages = new ArrayList<ArrayList<String>>(
			50);

	private int m_fontSize = 18;// 字体大小
	private int marginWidth = 15; // 左右与边缘的距离
	private int marginHeight = 20; // 上下与边缘的距离

	private int mLineCount; // 每页可以显示的行数
	private float mVisibleHeight; // 绘制内容的宽
	private float mVisibleWidth; // 绘制内容的宽
	private Paint mPaint;

	public Handler handler = null;

	Context context;
	private static BookContentManager instance = null;

	private boolean contentload = false;

	private BookContentManager(Context context) {
		this.context = context;
		mWidth = context.getResources().getDisplayMetrics().widthPixels;
		mHeight = context.getResources().getDisplayMetrics().heightPixels;
		mVisibleWidth = mWidth - marginWidth * 2;
		mVisibleHeight = mHeight - marginHeight * 2;
		mLineCount = (int) (mVisibleHeight / m_fontSize) - 1; // 可显示的行数

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setTextAlign(Align.LEFT);
		mPaint.setTextSize(m_fontSize);
	}

	public static synchronized BookContentManager getInstance(Context context) {
		if (instance == null) {
			instance = new BookContentManager(context);
		}
		return instance;
	}

	public void openbook(String strFilePath) throws IOException {
		pages.clear();
		m_mbBufLen = 0;
		m_mbBufBegin = 0;
		m_mbBufEnd = 0;
		setContentload(false);

		book_file = new File(strFilePath);
		long lLen = book_file.length();
		m_mbBufLen = (int) lLen;
		RandomAccessFile raf = new RandomAccessFile(book_file, "r");
		FileChannel fc = raf.getChannel();
		m_mbBuf = fc.map(FileChannel.MapMode.READ_ONLY, 0, lLen);
		ArrayList<String> lines = pageDown();
		long start = System.currentTimeMillis();

		while (!lines.isEmpty()) {
			pages.add(lines);
			lines = pageDown();
			if (pages.size() > 1 || isContentload()) {
				handler.sendEmptyMessage(0);
			}
		}
		
		handler.sendEmptyMessage(0);
		setContentload(true);
		fc.close();
		raf.close();
		Log.v("read", "time:" + (System.currentTimeMillis() - start));
	}

	protected byte[] readParagraphBack(int nFromPos) {
		int nEnd = nFromPos;
		int i;
		byte b0, b1;
		if (m_strCharsetName.equals("UTF-16LE")) {
			i = nEnd - 2;
			while (i > 0) {
				b0 = m_mbBuf.get(i);
				b1 = m_mbBuf.get(i + 1);
				if (b0 == 0x0a && b1 == 0x00 && i != nEnd - 2) {
					i += 2;
					break;
				}
				i--;
			}

		} else if (m_strCharsetName.equals("UTF-16BE")) {
			i = nEnd - 2;
			while (i > 0) {
				b0 = m_mbBuf.get(i);
				b1 = m_mbBuf.get(i + 1);
				if (b0 == 0x00 && b1 == 0x0a && i != nEnd - 2) {
					i += 2;
					break;
				}
				i--;
			}
		} else {
			i = nEnd - 1;
			while (i > 0) {
				b0 = m_mbBuf.get(i);
				if (b0 == 0x0a && i != nEnd - 1) {
					i++;
					break;
				}
				i--;
			}
		}
		if (i < 0)
			i = 0;
		int nParaSize = nEnd - i;
		int j;
		byte[] buf = new byte[nParaSize];
		for (j = 0; j < nParaSize; j++) {
			buf[j] = m_mbBuf.get(i + j);
		}
		return buf;
	}

	// 读取上一段落
	protected byte[] readParagraphForward(int nFromPos) {
		int nStart = nFromPos;
		int i = nStart;
		byte b0, b1;
		// 根据编码格式判断换行
		if (m_strCharsetName.equals("UTF-16LE")) {
			while (i < m_mbBufLen - 1) {
				b0 = m_mbBuf.get(i++);
				b1 = m_mbBuf.get(i++);
				if (b0 == 0x0a && b1 == 0x00) {
					break;
				}
			}
		} else if (m_strCharsetName.equals("UTF-16BE")) {
			while (i < m_mbBufLen - 1) {
				b0 = m_mbBuf.get(i++);
				b1 = m_mbBuf.get(i++);
				if (b0 == 0x00 && b1 == 0x0a) {
					break;
				}
			}
		} else {
			while (i < m_mbBufLen) {
				b0 = m_mbBuf.get(i++);
				if (b0 == 0x0a) {
					break;
				}
			}
		}
		int nParaSize = i - nStart;
		byte[] buf = new byte[nParaSize];
		for (i = 0; i < nParaSize; i++) {
			buf[i] = m_mbBuf.get(nFromPos + i);
		}
		return buf;
	}

	protected ArrayList<String> pageDown() {
		String strParagraph = "";
		ArrayList<String> lines = new ArrayList<String>();
		while (lines.size() < mLineCount && m_mbBufEnd < m_mbBufLen) {
			byte[] paraBuf = readParagraphForward(m_mbBufEnd); // 读取一个段落
			m_mbBufEnd += paraBuf.length;
			try {
				strParagraph = new String(paraBuf, m_strCharsetName);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String strReturn = "";
			if (strParagraph.indexOf("\r\n") != -1) {
				strReturn = "\r\n";
				strParagraph = strParagraph.replaceAll("\r\n", "");
			} else if (strParagraph.indexOf("\n") != -1) {
				strReturn = "\n";
				strParagraph = strParagraph.replaceAll("\n", "");
			}

			if (strParagraph.length() == 0) {
				lines.add(strParagraph);
			}
			while (strParagraph.length() > 0) {
				int nSize = mPaint.breakText(strParagraph, true, mVisibleWidth,
						null);
				lines.add(strParagraph.substring(0, nSize));
				strParagraph = strParagraph.substring(nSize);
				if (lines.size() >= mLineCount) {
					break;
				}
			}
			if (strParagraph.length() != 0) {
				try {
					m_mbBufEnd -= (strParagraph + strReturn)
							.getBytes(m_strCharsetName).length;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return lines;
	}

	protected void pageUp() {
		if (m_mbBufBegin < 0)
			m_mbBufBegin = 0;
		Vector<String> lines = new Vector<String>();
		String strParagraph = "";
		while (lines.size() < mLineCount && m_mbBufBegin > 0) {
			Vector<String> paraLines = new Vector<String>();
			byte[] paraBuf = readParagraphBack(m_mbBufBegin);
			m_mbBufBegin -= paraBuf.length;
			try {
				strParagraph = new String(paraBuf, m_strCharsetName);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			strParagraph = strParagraph.replaceAll("\r\n", "");
			strParagraph = strParagraph.replaceAll("\n", "");

			if (strParagraph.length() == 0) {
				paraLines.add(strParagraph);
			}
			while (strParagraph.length() > 0) {
				int nSize = mPaint.breakText(strParagraph, true, mVisibleWidth,
						null);
				paraLines.add(strParagraph.substring(0, nSize));
				strParagraph = strParagraph.substring(nSize);
			}
			lines.addAll(0, paraLines);
		}
		while (lines.size() > mLineCount) {
			try {
				m_mbBufBegin += lines.get(0).getBytes(m_strCharsetName).length;
				lines.remove(0);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		m_mbBufEnd = m_mbBufBegin;
		return;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pages.size();
	}

	@Override
	public String getItem(int position) {
		ArrayList<String> pLines = pages.get(position);
		StringBuilder strB = new StringBuilder(200);
		for (String line : pLines) {
			if (line.trim().length() > 0) {
				strB.append(line).append("\r\n");
			}
		}
		return strB.toString();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position) {
		TextView textView = new TextView(context);
		float fPercent = (float) ((position + 1) * 1.0 / pages.size());// (m_mbBufBegin
																		// * 1.0
																		// /
																		// m_mbBufLen);
		DecimalFormat df = new DecimalFormat("#0.0");
		String strPercent = df.format(fPercent * 100) + "%";
		textView.append(String.format(context.getString(R.string.numpage),
				(1 + position) + "/" + getCount(), strPercent) + "\r\n");

		textView.append(getItem(position));
		textView.setTextColor(Color.BLACK);
		textView.setTextSize(m_fontSize);
		textView.setBackgroundColor(Color.WHITE);
		textView.setBackgroundResource(R.drawable.book_bg);
		textView.setPadding(10, 10, 10, 10);

		return textView;
	}

	public void setContentload(boolean contentload) {
		this.contentload = contentload;
	}

	public boolean isContentload() {
		return contentload;
	}

	public void clear() {

	}
}
