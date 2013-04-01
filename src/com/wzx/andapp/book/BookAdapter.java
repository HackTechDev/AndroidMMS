package com.wzx.andapp.book;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wzx.andapp.shh.R;

public class BookAdapter implements IAdapter {
	private List<String> strList = new ArrayList<String>();
	private int position = 0;
	private Context mContext;

	public BookAdapter(Context context) {
		super();
		this.mContext = context;
	}

	public void addItem(List<String> list) {
		strList.addAll(list);
	}

	public int getCount() {
		return strList.size();
	}

	public String getItem(int position) {
		return strList.get(position);
	}

	// public String getNextPage() {
	// String result = strList.get(position++);
	// if (position >= strList.size()) {
	// position = strList.size();
	// }
	// return result;
	// }

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position) {
		TextView textView = new TextView(mContext);
		textView.setText(strList.get(position));
		textView.setTextColor(Color.BLACK);
		textView.setBackgroundColor(Color.WHITE);
		textView.setBackgroundResource(R.drawable.book_bg);
		// textView.setBackgroundResource(R.drawable.ly);
		textView.setPadding(10, 10, 10, 10);
		textView.setLayoutParams(new FrameLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return textView;
	}
}
