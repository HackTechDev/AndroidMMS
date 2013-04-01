package com.wzx.andapp.shh;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.wzx.andapp.rss.bo.RSSFeed;
import com.wzx.andapp.utils.DatabaseHelper;

public class RsslinkListActivity extends ActivityGroup {
	public final static int MENU_ADD = Menu.FIRST;
	public final static int MENU_DELETE = Menu.FIRST + 1;

	Activity activity;

	// List<String> group;
	// List<List<String>> child;

	ExpandableListView expandList;

	Dialog dialogAdd;
	Dialog dialogDelete;

	AlertDialog addAlertDialog;

	View viewAdd;
	View viewDelete;

	OnClickListener clickListener;

	EditText rss_title_etext;
	EditText rss_url_etext;
	EditText add_sex;
	EditText add_home;
	Button ok_btn, cancel_btn;

	EditText delete_id;
	Button delete_ok, delete_no;

	public final String RSS_URL = "http://rss.news.sohu.com/rss/guonei.xml";
	private RSSFeed feed = null;
	private ProgressDialog pd;

	DatabaseHelper mDbHelper = null;
	SQLiteDatabase mDb = null;
	private String TABLE_NAME = "rsslink";
	private String NAME = "name";
	private String LINK = "rsslink";
	private ListView listView;
	SimpleCursorAdapter listAdapter;
	Cursor cursor;
	public static ArrayList<View> history;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//
		this.history = new ArrayList<View>();
		Context mContext = this;
		mDbHelper = DatabaseHelper.getInstance(mContext);
		mDb = mDbHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", "sohu");
		values.put("rsslink", "http://rss.news.sohu.com/rss/guonei.xml");
		// insert(values);
		setContentView(R.layout.rss_links);
		listView = (ListView) findViewById(R.id.itemlist);
		load_data();
		activity = this;
		final LocalActivityManager m = getLocalActivityManager();

		cursor = findAll();
		if (cursor == null || cursor.getCount() < 1) {
			insert(values);
			cursor = findAll();
		}
		startManagingCursor(cursor);
		listAdapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_expandable_list_item_2, cursor,
				new String[] { NAME, LINK }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 由于上面第三条日志信息输出的类型为SQLiteCursor，所以采用Cursor的方式获取数据。
				Cursor cursor = (Cursor) ((ListView) parent)
						.getItemAtPosition(position);
				if (cursor != null && cursor.moveToPosition(position)) {
					String rsslink = cursor.getString(2);
					Intent intent = new Intent();
					intent.putExtra("RSS_URL", rsslink);
					intent.setClass(RsslinkListActivity.this,
							RssExpandActivity.class);
					startActivity(intent);
					// Window w = m.startActivity("itemlist",
					// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
					// View v = w.getDecorView();
					// LinearLayout container = (LinearLayout)
					// findViewById(R.id.links_layout);
					// container.removeAllViews();
					// container.addView(v);
					// history.add(v);
					overridePendingTransition(R.anim.scale_rotate,
							R.anim.my_alpha_action);
				}
				cursor.close();
			}
		});
		//createDialogAdd();
		// createDialogDelete();
	}

	private void load_data() {
		// pd = ProgressDialog.show(this,
		// getResources().getString(R.string.load_content_title),
		// getResources().getString(R.string.load_content_msg), true,
		// false);
	}

	public void createDialogAdd() {
		viewAdd = this.getLayoutInflater().inflate(R.layout.rss_item_add, null);

//		dialogAdd = new Dialog(this);
//		dialogAdd.setContentView(viewAdd, new LayoutParams(
//				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
//		dialogAdd.setTitle("输入新成员信息");

		rss_title_etext = (EditText) viewAdd.findViewById(R.id.rss_title_etext);
		rss_url_etext = (EditText) viewAdd.findViewById(R.id.rss_url_etext);
		// add_sex = (EditText) viewAdd.findViewById(R.id.add_sex);
		// add_home = (EditText) viewAdd.findViewById(R.id.add_home);

//		ok_btn = (Button) viewAdd.findViewById(R.id.ok_btn);
//		cancel_btn = (Button) viewAdd.findViewById(R.id.cancel_btn);
		addAlertDialog = new AlertDialog.Builder(this)
				.setTitle("添加RSS")
				.setView(viewAdd)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						showDialog("姓名 ："
								+ rss_title_etext.getText().toString() + "密码："
								+ rss_url_etext.getText().toString());
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				}).show();
		// dialogAdd.setPositiveButton("确定",
		// new DialogInterface.OnClickListener() {
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// String[] data = { rss_title_etext.getText().toString(),
		// rss_url_etext.getText().toString() };
		//
		// // addInfo(rss_title_etext.getText().toString(), data);
		// Toast.makeText(
		// RsslinkListActivity.this,
		// rss_title_etext.getText().toString() + "="
		// + rss_url_etext.getText().toString(),
		// Toast.LENGTH_SHORT).show();
		// ContentValues values = new ContentValues();
		// values.put("name", rss_title_etext.getText().toString().trim());
		// values.put("rsslink", rss_url_etext.getText().toString().trim());
		// insert(values);
		// dialogAdd.dismiss();
		// cursor.requery();
		// }
		// });
//		ok_btn.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				String[] data = { rss_title_etext.getText().toString(),
//						rss_url_etext.getText().toString() };
//
//				// addInfo(rss_title_etext.getText().toString(), data);
//				Toast.makeText(
//						RsslinkListActivity.this,
//						rss_title_etext.getText().toString() + "="
//								+ rss_url_etext.getText().toString(),
//						Toast.LENGTH_SHORT).show();
//				ContentValues values = new ContentValues();
//				values.put("name", rss_title_etext.getText().toString().trim());
//				values.put("rsslink", rss_url_etext.getText().toString().trim());
//				insert(values);
//				dialogAdd.dismiss();
//				cursor.requery();
//			}
//		});
//
//		cancel_btn.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				dialogAdd.dismiss();
//			}
//		});
	}

	private void showDialog(String str) {
		new AlertDialog.Builder(RsslinkListActivity.this).setMessage(str)
				.show();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_ADD, 0, "新增");
		menu.add(0, MENU_DELETE, 0, "删除");

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ADD:
			createDialogAdd();//dialogAdd.show();
			break;

		case MENU_DELETE:
			dialogDelete.show();
			break;
		}
		return false;
	}

	protected void onDestroy() {
		if (mDb != null) {
			mDb.close();
		}
		if (mDbHelper != null) {
			mDbHelper.close();
		}
		super.onDestroy();
	}

	/**
	 * 插入一条数据
	 * 
	 * @param key
	 * @param date
	 */
	public void insert(String key, String date) {
		ContentValues values = new ContentValues();
		values.put(key, date);
		mDb.insert(TABLE_NAME, null, values);
	}

	public void insert(ContentValues values) {
		mDb.insert(TABLE_NAME, null, values);
	}

	/**
	 * 删除一掉数据
	 * 
	 * @param key
	 * @param date
	 */
	public void delete(String key, String date) {
		mDb.delete(TABLE_NAME, key + "=?", new String[] { date });
	}

	/**
	 * 更新一条数据
	 * 
	 * @param key
	 * @param oldDate
	 * @param newDate
	 */
	public void update(String key, String oldDate, String newDate) {
		ContentValues values = new ContentValues();
		values.put(key, newDate);
		mDb.update(TABLE_NAME, values, key + "=?", new String[] { oldDate });
	}

	/**
	 * 查找一条数据
	 * 
	 * @param key
	 * @param date
	 * @return
	 */
	public Cursor find(String key, String date) {

		Cursor cursor = mDb.query(TABLE_NAME, null, key + "=?",
				new String[] { date }, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	public Cursor findAll() {

		Cursor cursor = mDb.query(TABLE_NAME,// new String[] { "name", "rsslink"
												// }
				null, null, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	public void back() {
		if (history.size() > 1) {
			history.remove(history.size() - 1);
			setContentView(history.get(history.size() - 1));
		} else {
			finish();
		}
	}

	// public void onBackPressed() {
	// back();
	// return;
	// }
	// public void onBackPressed() {
	// // TODO Auto-generated method stub
	// // if (MathHelper.SHOW_DETAILS) {
	// // Log.e("back", "pressed accepted");
	// // Constants.LIST_ACTIVITY = 1;
	// // Constants.SHOW_DETAILS = false;
	// Intent intent = new Intent(this, MyTab.class);
	// startActivity(intent);
	// finish();
	// // super.onBackPressed();
	// // }
	// }

}