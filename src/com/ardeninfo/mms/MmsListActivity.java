package com.ardeninfo.mms;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ardeninfo.mms.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MmsListActivity extends Activity {
	private final String TAG = "SmsPage";

	private final Uri CONTENT_URI = Uri.parse("content://mms/inbox"); 

	private final Uri CONTENT_URI_PART = Uri.parse("content://mms/part"); 

	private static final String ADDRESS_STR = "content://mms/%1$s/addr";

	private static final String MSG_ID_STR = "msg_id=%1$s";

	private ListView mmsList;

	Cursor cursor = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mms_list_item);
		mmsList = (ListView) findViewById(R.id.mmslist);
		cursor = getContentResolver()
				.query(CONTENT_URI, null, null, null, null);
		System.out.println(cursor.getCount() + "==FFF");
		if (cursor.getCount() > 0) {
			MmsAdapter adapter = new MmsAdapter(this, R.layout.mms_item,
					cursor, new String[] {}, new int[] {});
			mmsList.setAdapter(adapter);
		}
		mmsList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(MmsListActivity.this.getParent(), position + "."
						+ id, Toast.LENGTH_SHORT);
				Intent intent = new Intent();
				intent.putExtra("msg_id", id);
				intent.setClass(MmsListActivity.this, MmsReaderActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.scale_rotate,
						R.anim.my_alpha_action);
			}
		});
		mmsList.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				
				java.lang.System.out.println(((ListView) v)
						.getSelectedItemPosition()
						+ " ### "
						+ ((ListView) v).getSelectedItemId());
				menu.add(0, 0, 0, "Information");
				menu.add(0, 1, 0, "Suppression");
			}
		});
		// cursor.close();
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		ContextMenuInfo menuInfo = (ContextMenuInfo) item.getMenuInfo();
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		
		java.lang.System.out.println(info.id + " @@@ " + info.position);
		if (1 == item.getItemId()) {
			deleteMms(info.id);
		}
		Toast.makeText(
				this.getParent(),
				"ItemId: " + item.getItemId() + "info.id :" + info.id + " position : "
						+ info.position, Toast.LENGTH_LONG).show();
		return super.onContextItemSelected(item);
	}

	public class MmsAdapter extends SimpleCursorAdapter {

		public MmsAdapter(Context context, int layout, Cursor c, String[] from,
				int[] to) {
			super(context, layout, c, from, to);
		}

		public final View newView(final Context context, final Cursor cursor,
				final ViewGroup parent) {
			final LayoutInflater inflater = LayoutInflater.from(context);
			final View v = inflater.inflate(R.layout.mms_item, null);
			return v;
		}

		public void bindView(View view, Context context, Cursor cursor) {
			TextView from = (TextView) view.findViewById(R.id.MmsFrom);
			TextView sub = (TextView) view.findViewById(R.id.MmsSub);
			TextView date = (TextView) view.findViewById(R.id.MmsDate);
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date time = new Date(
					cursor.getLong(cursor.getColumnIndex("date")) * 1000);
			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String subject = cursor.getString(cursor.getColumnIndex("sub"));
			if (!"".equals(subject) && subject != null) {
				try {
					sub.setText(":"
							+ new String(subject.getBytes("iso-8859-1"),
									"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			String selectionAdd = String.format(MSG_ID_STR, id);

			Uri uriAddr = Uri.parse(String.format(ADDRESS_STR, id));
			Cursor cAdd = getContentResolver().query(uriAddr, null,
					selectionAdd, null, null);
			String fromNo = null;
			if (cAdd.moveToFirst()) {
				do {
					String number = cAdd.getString(cAdd
							.getColumnIndex("address"));
					if (number != null) {
						try {
							Long.parseLong(number.replace("-", ""));
							fromNo = number;
						} catch (NumberFormatException nfe) {
							if (fromNo == null) {
								fromNo = number;
							}
						}
					}
				} while (cAdd.moveToNext());
			}
			if (cAdd != null) {
				cAdd.close();
			}
			from.setText(fromNo);
			date.setText(format.format(time));
			super.bindView(view, context, cursor);
		}

	}

	public class MyAdapter extends SimpleCursorAdapter {
		private String name = "";

		public MyAdapter(Context context, int layout, Cursor c, String[] from,
				int[] to) {
			super(context, layout, c, from, to);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			TextView address = null;// (TextView)
									// view.findViewById(R.id.sms_address);
			TextView body = null;// (TextView) view.findViewById(R.id.sms_body);
			TextView date = null;// (TextView) view.findViewById(R.id.sms_date);
			TextView sub = null;// (TextView) view.findViewById(R.id.sms_sub);
			ImageView image = null;// (ImageView)
									// view.findViewById(R.id.sms_image);

			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date time = new Date(
					cursor.getLong(cursor.getColumnIndex("date")) * 1000);
			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String subject = cursor.getString(cursor.getColumnIndex("sub"));
			Cursor cAdd = null;
			Cursor cPhones = null;
			Cursor cPeople = null;
			Cursor cPart = null;
			try {
				
				String selectionAdd = new String("msg_id=" + id);
				Uri uriAddr = Uri.parse("content://mms/" + id + "/addr");
				cAdd = getContentResolver().query(uriAddr, null, selectionAdd,
						null, null);

				
				if (cAdd.moveToFirst()) {
					String number = cAdd.getString(cAdd
							.getColumnIndex("address"));
					cPhones = null;// getContentResolver().query(ContactsContract.Phones.CONTENT_URI,
									// new
									// String[]{Contacts.Phones.PERSON_ID},Contacts.Phones.NUMBER
									// +"=?",new String[]{number}, null);
					if (cPhones.getCount() > 0) {						
						while (cPhones.moveToNext()) {
							String pId = null;// cPhones.getString(cPhones.getColumnIndex(Contacts.Phones.PERSON_ID));
							Uri uriPeo = null;// Uri.parse(Contacts.People.CONTENT_URI+"/"+pId);
							cPeople = getContentResolver().query(uriPeo, null,
									null, null, null);
							if (cPeople.getCount() > 0) {
								String str = "";
								while (cPeople.moveToNext()) {
									if (str == "") {
										// str =
										// cPeople.getString(cPeople.getColumnIndex(Contacts.People.DISPLAY_NAME));
									} else {
										// str +=
										// ","+cPeople.getString(cPeople.getColumnIndex(Contacts.People.DISPLAY_NAME));
									}
								}
								name = number + "/" + str;
															
							} else {
								name = number;
							}
						}
					} else {
						name = number;
					}
				}

			
				String selectionPart = new String("mid=" + id);
				cPart = getContentResolver().query(CONTENT_URI_PART, null,
						selectionPart, null, null);
				String bodyStr = "";
				String[] coloumns = null;
				String[] values = null;
				while (cPart.moveToNext()) {
					coloumns = cPart.getColumnNames();
					if (values == null)
						values = new String[coloumns.length];
					for (int i = 0; i < cPart.getColumnCount(); i++) {
						values[i] = cPart.getString(i);
					}
					if (values[3].equals("image/jpeg")
							|| values[3].equals("image/bmp")) {
						image.setImageBitmap(getMmsImage(values[0]));
						image.setVisibility(View.VISIBLE);
					} else if (values[3].equals("text/plain")) {
						
						if (values[12] != null) {
							bodyStr = getMmsText(values[0]);
						} else {
							bodyStr = values[13];
						}
					}
				}
				if (!"".equals(subject) && subject != null) {
					try {
						sub.setText(new String(subject.getBytes("iso-8859-1"),
								"UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				if (!"".equals(bodyStr)) {
					body.setText(bodyStr);
				}
				address.setText(name);
				date.setText(format.format(time));
			} catch (RuntimeException e) {
				Log.e(TAG, e.getMessage());
			} finally {
				if (cAdd != null) {
					cAdd.close();
				}
				if (cPart != null) {
					cPart.close();
				}
				if (cPeople != null) {
					cPeople.close();
				}
				if (cPhones != null) {
					cPhones.close();
				}
			}
			super.bindView(view, context, cursor);
		}
	}

	private String getMmsText(String _id) { 
		Uri partURI = Uri.parse("content://mms/part/" + _id);
		InputStream is = null;
		StringBuilder sb = new StringBuilder();
		try {
			is = getContentResolver().openInputStream(partURI);
			if (is != null) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));
				String temp = reader.readLine();
				while (temp != null) {
					sb.append(temp);
					temp = reader.readLine();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			Log.v(TAG, "��ȡ�����쳣" + e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					Log.v(TAG, "��ȡ�����쳣" + e.getMessage());
				}
			}
		}
		return sb.toString();
	}

	private Bitmap getMmsImage(String _id) { 
		Uri partURI = Uri.parse("content://mms/part/" + _id);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;
		Bitmap bitmap = null;
		try {
			is = getContentResolver().openInputStream(partURI);
			byte[] buffer = new byte[256];
			int len = -1;
			while ((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0,
					baos.toByteArray().length);
		} catch (IOException e) {
			e.printStackTrace();
			Log.v(TAG, "��ȡͼƬ�쳣" + e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					Log.v(TAG, "��ȡͼƬ�쳣" + e.getMessage());
				}
			}
		}
		return bitmap;
	}

	/**
	 *
	 * 
	 * @param context
	 * @param phoneNum
	 * @return
	 */
	public static String getContactNameFromPhoneNum(Context context,
			String phoneNum) {
		String contactName = "";
		ContentResolver cr = context.getContentResolver();
		Cursor pCur = cr.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
				ContactsContract.CommonDataKinds.Phone.NUMBER + " = ?",
				new String[] { phoneNum }, null);
		if (pCur.moveToFirst()) {
			contactName = pCur
					.getString(pCur
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			pCur.close();
		}
		return contactName;
	}

	public void deleteMms(long id) {
		int rcount = getContentResolver().delete(
				Uri.parse("content://mms/" + id), null, null);
		Toast.makeText(MmsListActivity.this, "rcount : " + rcount,
				Toast.LENGTH_LONG);
		// ((SimpleCursorAdapter) mmsList.getAdapter()).notifyDataSetChanged();
		cursor.requery();
	}

	@Override
	protected void onDestroy() {
		cursor.close();
		super.onDestroy();
	}
}
