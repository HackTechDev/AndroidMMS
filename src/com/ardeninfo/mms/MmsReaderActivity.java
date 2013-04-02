package com.ardeninfo.mms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import com.ardeninfo.mms.R;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MmsReaderActivity extends Activity {
	private final Uri CONTENT_URI_PART = Uri.parse("content://mms/part");

	private static final String MSG_ID_STR = "mid=%1$s";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		long msg_id = this.getIntent().getLongExtra("msg_id", 0);
		// final LayoutInflater inflater = LayoutInflater.from(this);
		// final ViewGroup v = (ViewGroup) inflater.inflate(
		// R.layout.mms_item_detail, null);
		setContentView(R.layout.mms_item_detail);

		ViewGroup listview = (ViewGroup) findViewById(R.id.mmsdetaillist);
		Uri uri = Uri.parse("content://mms/");
		String selection = "_id = " + msg_id;
		Cursor mcursor = getContentResolver().query(uri, null, selection, null,
				null);

		if (mcursor.moveToFirst()) {
			String subject = mcursor.getString(mcursor.getColumnIndex("sub"));
			if (!"".equals(subject) && subject != null) {
				try {
					setTitle(new String(subject.getBytes("iso-8859-1"), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}

		Cursor cursor = getContentResolver().query(CONTENT_URI_PART, null,
				String.format(MSG_ID_STR, msg_id), null, null);

		if (cursor.moveToFirst()) {
			do {
				String partId = cursor.getString(cursor.getColumnIndex("_id"));
				String type = cursor.getString(cursor.getColumnIndex("ct"));
				if ("text/plain".equals(type)) {
					String data = cursor.getString(cursor
							.getColumnIndex("_data"));
					String body;
					if (data != null) {
						// implementation of this method below
						body = getMmsText(partId);
					} else {
						body = cursor.getString(cursor.getColumnIndex("text"));
					}
					TextView t = new TextView(this);
					t.setTextSize(18);
					t.setText(body);
					listview.addView(t);
				} else if ("image/jpeg".equals(type)
						|| "image/bmp".equals(type) || "image/gif".equals(type)
						|| "image/jpg".equals(type) || "image/png".equals(type)) {
					Bitmap bitmap = getMmsImage(partId);
					ImageView iv = new ImageView(this);
					iv.setImageBitmap(bitmap);
					listview.addView(iv);
				}
			} while (cursor.moveToNext());
		}
		cursor.close();

	}

	public void getAllMms() {
		Uri uri = Uri.parse("content://mms/");
		String selection = null;// "_id = " + mmsId;
		Cursor cursor = getContentResolver().query(uri, null, selection, null,
				null);
		String[] temp = cursor.getColumnNames();
		for (int i = 0; i < temp.length; i++)
			System.out.println(i + ":" + temp[i]);
		cursor.close();
	}

	/**
	 * 
	 */
	// _id is the ID of the message. Captain obvious to the rescue? Not really.
	// This ID can be used to retrieve detailed information using either
	// content://sms or content://mms.
	// date no explanation needed.
	// thread_id is the ID of the conversation
	// body The content of the last SMS on this conversation.
	// If it's an MMS, even if it has a text part, this will be null.

	public void getSmsMms() {
		// usually, when you call query and want to return all columns you can
		// pass null as the projection parameter. However, you can not do that
		// with this provider, so that's why I'm using *
		ContentResolver contentResolver = getContentResolver();
		final String[] projection = new String[] { "*" };
		Uri uri = Uri.parse("content://mms-sms/conversations/");
		Cursor cursor = contentResolver
				.query(uri, projection, null, null, null);
		String[] temp = cursor.getColumnNames();
		for (int i = 0; i < temp.length; i++)
			System.out.println(i + ":" + temp);

	}

	public void getMms(String mmsId) {
		Uri uri = Uri.parse("content://mms/");
		String selection = "_id = " + mmsId;
		Cursor cursor = getContentResolver().query(uri, null, selection, null,
				null);
	}

	public void getAllText(String mmsId) {
		String selectionPart = "mid=" + mmsId;
		Uri uri = Uri.parse("content://mms/part");
		Cursor cursor = getContentResolver().query(uri, null, selectionPart,
				null, null);
		if (cursor.moveToFirst()) {
			do {
				String partId = cursor.getString(cursor.getColumnIndex("_id"));
				String type = cursor.getString(cursor.getColumnIndex("ct"));
				if ("text/plain".equals(type)) {
					String data = cursor.getString(cursor
							.getColumnIndex("_data"));
					String body;
					if (data != null) {
						// implementation of this method below
						body = getMmsText(partId);
					} else {
						body = cursor.getString(cursor.getColumnIndex("text"));
					}
				}
			} while (cursor.moveToNext());
		}

	}

	public String getMmsText(String id) {
		Uri partURI = Uri.parse("content://mms/part/" + id);
		InputStream is = null;
		StringBuilder sb = new StringBuilder();
		try {
			is = getContentResolver().openInputStream(partURI);
			if (is != null) {
				InputStreamReader isr = new InputStreamReader(is, "UTF-8");
				BufferedReader reader = new BufferedReader(isr);
				String temp = reader.readLine();
				while (temp != null) {
					sb.append(temp);
					temp = reader.readLine();
				}
			}
		} catch (IOException e) {
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return sb.toString();
	}

	public void getAllImg(String mmsId) {
		String selectionPart = "mid=" + mmsId;
		Uri uri = Uri.parse("content://mms/part");
		Cursor cPart = getContentResolver().query(uri, null, selectionPart,
				null, null);
		if (cPart.moveToFirst()) {
			do {
				String partId = cPart.getString(cPart.getColumnIndex("_id"));
				String type = cPart.getString(cPart.getColumnIndex("ct"));
				if ("image/jpeg".equals(type) || "image/bmp".equals(type)
						|| "image/gif".equals(type) || "image/jpg".equals(type)
						|| "image/png".equals(type)) {
					Bitmap bitmap = getMmsImage(partId);
				}
			} while (cPart.moveToNext());
		}

	}

	public Bitmap getMmsImage(String _id) {
		Uri partURI = Uri.parse("content://mms/part/" + _id);
		InputStream is = null;
		Bitmap bitmap = null;
		try {
			is = getContentResolver().openInputStream(partURI);
			bitmap = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return bitmap;
	}

	public String getAddressNumber(int id) {
		String selectionAdd = new String("msg_id=" + id);
		String uriStr = MessageFormat.format("content://mms/{0}/addr", id);
		Uri uriAddress = Uri.parse(uriStr);
		Cursor cAdd = getContentResolver().query(uriAddress, null,
				selectionAdd, null, null);
		String name = null;
		if (cAdd.moveToFirst()) {
			do {
				String number = cAdd.getString(cAdd.getColumnIndex("address"));
				if (number != null) {
					try {
						Long.parseLong(number.replace("-", ""));
						name = number;
					} catch (NumberFormatException nfe) {
						if (name == null) {
							name = number;
						}
					}
				}
			} while (cAdd.moveToNext());
		}
		if (cAdd != null) {
			cAdd.close();
		}
		return name;
	}

	/**
	 * Mark a single SMS/MMS message as being read or not.
	 * 
	 * @param context
	 *            - The current context of this Activity.
	 * @param messageID
	 *            - The Message ID that we want to alter.
	 * 
	 * @return boolean - Returns true if the message was updated successfully.
	 */
	public static boolean setMessageRead(Context context, long messageID,
			boolean isViewed) {
		try {
			if (messageID == 0) {
				return false;
			}
			ContentValues contentValues = new ContentValues();
			if (isViewed) {
				contentValues.put("READ", 1);
			} else {
				contentValues.put("READ", 0);
			}
			String selection = null;
			String[] selectionArgs = null;
			context.getContentResolver().update(
					Uri.parse("content://mms/" + messageID), contentValues,
					selection, selectionArgs);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

}