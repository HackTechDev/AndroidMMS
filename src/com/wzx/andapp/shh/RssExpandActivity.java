package com.wzx.andapp.shh;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.wzx.andapp.rss.bo.RSSFeed;
import com.wzx.andapp.rss.sax.RSSHandler;
import com.wzx.andapp.rss.sax.XmlPullService;
import com.wzx.andapp.utils.FileUtils;

public class RssExpandActivity extends Activity {
	public final static int MENU_ADD = Menu.FIRST;
	public final static int MENU_DELETE = Menu.FIRST + 1;

	Activity activity;

	// List<String> group;
	// List<List<String>> child;

	ExpandableListView expandList;
	InfoDetailsAdapter mAdapter;

	Dialog dialogAdd;
	Dialog dialogDelete;

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

	public String RSS_URL = "http://rss.news.sohu.com/rss/guonei.xml";
	private RSSFeed feed = null;
	private ProgressDialog pd;
	private int expandFlag = -1;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(RssExpandActivity.this, getResources()
						.getString(R.string.net_access_tip_rssexpandactivity),
						Toast.LENGTH_LONG);
				pd.dismiss();

				break;
			default:
				pd.dismiss();
				setContentView(R.layout.expand_list_main);
				// View contentView = LayoutInflater.from(
				// RssExpandActivity.this.getParent()).inflate(
				// R.layout.expand_list_main, null);

				expandList = (ExpandableListView) findViewById(R.id.expandList);

				mAdapter = new InfoDetailsAdapter(RssExpandActivity.this);
				// mAdapter = new InfoDetailsAdapter(
				// RssExpandActivity.this.getParent());
				expandList.setAdapter(mAdapter);

				expandList.setBackgroundColor(Color.argb(150, 22, 70, 150));

				// setContentView(contentView);
				// RsslinkListActivity.history.add(contentView);

				expandList.setOnGroupClickListener(new OnGroupClickListener() {
					@Override
					public boolean onGroupClick(ExpandableListView arg0,
							View arg1, int groupPosition, long id) {
						if (expandFlag == -1) {
							// 展开被选的group
							expandList.expandGroup(groupPosition);
							// 设置被选中的group置于顶端
							expandList.setSelectedGroup(groupPosition);
							expandFlag = groupPosition;
						} else if (expandFlag == groupPosition) {
							expandList.collapseGroup(expandFlag);
							expandFlag = -1;
						} else {
							expandList.collapseGroup(expandFlag);
							// 展开被选的group
							expandList.expandGroup(groupPosition);
							// 设置被选中的group置于顶端
							expandList.setSelectedGroup(groupPosition);
							expandFlag = groupPosition;
						}

						return true;

					}

				});

				expandList.setOnChildClickListener(new OnChildClickListener() {
					@Override
					public boolean onChildClick(ExpandableListView arg0,
							View arg1, int arg2, int arg3, long arg4) {
						// TODO Auto-generated method stub
						Toast.makeText(activity,
								"[Child Click]:" + arg2 + ":" + arg3,
								Toast.LENGTH_LONG).show();

						return false;
					}

				});
				break;
			}

		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//
		Intent intent = this.getIntent();
		if (intent.hasExtra("RSS_URL")) {
			RSS_URL = intent.getStringExtra("RSS_URL");
		}
		load_data();
		activity = this;

		// createDialogAdd();
		// createDialogDelete();

	}

	private void load_data() {
		pd = ProgressDialog.show(this,
				getResources().getString(R.string.load_content_title),
				getResources().getString(R.string.load_content_msg), true,
				false);

		Thread thread = new MyRunnable();
		thread.start();
	}

	class MyRunnable extends Thread {
		public void run() {
			feed = getFeed(RSS_URL);
			if (feed == null) {
				feed = new RSSFeed();
				handler.sendMessage(handler.obtainMessage(1));
			} else {
				handler.sendMessage(handler.obtainMessage(0));
			}
		}
	}

	private RSSFeed getFeed(String urlString) {
		InputStream rssStream = null;
		InputStream fStream = null;
		try {

			// SAXParserFactory factory = SAXParserFactory.newInstance();
			// SAXParser parser = factory.newSAXParser();
			// XMLReader xmlreader = parser.getXMLReader();
			//
			// RSSHandler rssHandler = new RSSHandler();
			// xmlreader.setContentHandler(rssHandler);
			FileUtils fu = new FileUtils();
			String path = "com.rss/data/";
			String fileName = urlString;

			if (urlString.startsWith("http://")) {
				fileName = urlString.substring(7);
			}
			fileName = fileName.replaceAll("/", "_");
			File xmlfile = new File(fu.getSDPATH() + path + fileName);
			if (!xmlfile.exists()
					|| (java.lang.System.currentTimeMillis() - xmlfile
							.lastModified()) > 3600 * 1000 * 2) {
				rssStream = reqUrl(urlString, fu, path, fileName);
			}

			fStream = new FileInputStream(xmlfile);
			// InputSource is = new InputSource(fStream);
			// xmlreader.parse(is);

			// return rssHandler.getFeed();
			return XmlPullService.ReadXmlByPull3(fStream);
		} catch (Exception ee) {
			ee.printStackTrace();
			return null;
		} finally {
			try {
				if (rssStream != null) {
					rssStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (fStream != null) {
					fStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private InputStream reqUrl(String urlString, FileUtils fu, String path,
			String fileName) throws MalformedURLException, IOException {
		InputStream rssStream;
		URL url = new URL(urlString);

		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		URLConnection urlCon = null;
		if (!wifiManager.isWifiEnabled()) {
			Uri uri = Uri.parse("content://telephony/carriers/preferapn"); // 获取当前正在使用的APN接入点
			Cursor mCursor = this.getContentResolver().query(uri, null, null,
					null, null);
			if (mCursor != null) {
				mCursor.moveToNext(); // 游标移至第一条记录，当然也只有一条

				String proxyStr = mCursor.getString(mCursor
						.getColumnIndex("proxy"));
				int port = mCursor.getInt(mCursor.getColumnIndex("port"));

				if (proxyStr != null && proxyStr.trim().length() > 0) {
					InetSocketAddress proxyAddress = new InetSocketAddress(
							proxyStr, port);
					urlCon = url.openConnection(new Proxy(Proxy.Type.HTTP,
							proxyAddress));
				}
			}
		}
		if (urlCon == null) {
			urlCon = url.openConnection();
		}
		rssStream = urlCon.getInputStream();// url.openStream();
		fu.write2SDFromInput(path, fileName, rssStream);
		return rssStream;
	}

	public void createDialogAdd() {
		viewAdd = this.getLayoutInflater().inflate(R.layout.rss_item_add, null);

		dialogAdd = new Dialog(this);
		dialogAdd.setContentView(viewAdd);
		dialogAdd.setTitle("输入新成员信息");

		rss_title_etext = (EditText) viewAdd.findViewById(R.id.rss_title_etext);
		rss_url_etext = (EditText) viewAdd.findViewById(R.id.rss_url_etext);
		// add_sex = (EditText) viewAdd.findViewById(R.id.add_sex);
		// add_home = (EditText) viewAdd.findViewById(R.id.add_home);

		// ok_btn = (Button) viewAdd.findViewById(R.id.ok_btn);
		// cancel_btn = (Button) viewAdd.findViewById(R.id.cancel_btn);

		// ok_btn.setOnClickListener(new OnClickListener() {
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// String[] data = { rss_url_etext.getText().toString(),
		// rss_url_etext.getText().toString() };
		//
		// // addInfo(rss_title_etext.getText().toString(), data);
		// Toast.makeText(
		// RssExpandActivity.this,
		// rss_url_etext.getText().toString() + "="
		// + rss_url_etext.getText().toString(),
		// Toast.LENGTH_SHORT).show();
		// dialogAdd.dismiss();
		// mAdapter.notifyDataSetChanged();
		// }
		// });
		//
		// cancel_btn.setOnClickListener(new OnClickListener() {
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// dialogAdd.dismiss();
		// }
		// });
	}

	// public void createDialogDelete(){
	// viewDelete = this.getLayoutInflater().inflate(R.layout.delete, null);
	//
	// dialogDelete = new Dialog(this);
	// dialogDelete.setContentView(viewDelete);
	// dialogDelete.setTitle("删除指定成员");
	//
	// delete_id = (EditText)viewDelete.findViewById(R.id.delete_id);
	// delete_ok = (Button)viewDelete.findViewById(R.id.delete_ok);
	// delete_no = (Button)viewDelete.findViewById(R.id.delete_no);
	//
	// delete_ok.setOnClickListener(new OnClickListener(){
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	//
	// String id = delete_id.getText().toString();
	//
	// if(! id.equals("")){
	// int i = Integer.parseInt(id);
	// group.remove(i);
	// child.remove(i);
	//
	// dialogDelete.dismiss();
	//
	// mAdapter.notifyDataSetChanged();
	// }
	//
	//
	// }
	// });
	//
	// delete_no.setOnClickListener(new OnClickListener(){
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// dialogDelete.dismiss();
	// }
	// });
	// }

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_ADD, 0, "新增");
		menu.add(0, MENU_DELETE, 0, "删除");

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ADD:
			dialogAdd.show();
			break;

		case MENU_DELETE:
			dialogDelete.show();
			break;
		}
		return false;
	}

	static class GroupViewHolder {
		TextView tv_title;
		TextView tv_pubdate;
	}

	static class ChildViewHolder {
		TextView tv_content;
		Button btn;
	}

	public class InfoDetailsAdapter extends BaseExpandableListAdapter {
		Activity activity;

		public InfoDetailsAdapter(Activity a) {
			activity = a;
		}

		// child method stub

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return feed.getItem(groupPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return 1;// child.get(groupPosition).size();
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			// String string = child.get(groupPosition).get(childPosition);
			return getGenericChildView(groupPosition, childPosition,
					isLastChild, convertView, parent);
		}

		// group method stub
		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return feed.getItem(groupPosition);
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return feed.getAllItems().size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			// String string = group.get(groupPosition);
			return getGenericGroupView(groupPosition, isExpanded, convertView,
					parent);
		}

		// View stub to create Group/Children 's View
		public TextView getGenericView(String s) {
			// Layout parameters for the ExpandableListView
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, 64);

			TextView text = new TextView(activity);
			text.setLayoutParams(lp);
			// Center the text vertically
			text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			// Set the text starting position
			text.setPadding(36, 0, 0, 0);

			text.setText(s);
			return text;
		}

		public View getGenericGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			GroupViewHolder holder = null;
			if (convertView == null) {
				// Layout parameters for the ExpandableListView
				convertView = View.inflate(RssExpandActivity.this,
						R.layout.rss_itemrow, null);
				holder = new GroupViewHolder();
				convertView.setTag(holder);
			} else {
				holder = (GroupViewHolder) convertView.getTag();
			}
			holder.tv_title = (TextView) convertView.findViewById(R.id.title);
			String title = feed.getItem(groupPosition).getTitle();
			holder.tv_title.setText(title);
			holder.tv_pubdate = (TextView) convertView
					.findViewById(R.id.pubdate);
			String pubDate = feed.getItem(groupPosition).getPubDate();
			holder.tv_pubdate.setText(pubDate);
			return convertView;
		}

		public View getGenericChildView(final int groupPosition,
				int childPosition, boolean isLastChild, View convertView,
				ViewGroup parent) {
			ChildViewHolder cHolder = null;
			if (convertView == null) {
				convertView = View.inflate(RssExpandActivity.this,
						R.layout.rss_itemdetail, null);
				cHolder = new ChildViewHolder();
				cHolder.tv_content = (TextView) convertView
						.findViewById(R.id.content);
				cHolder.btn = (Button) convertView.findViewById(R.id.btn_url);
				convertView.setTag(cHolder);
			} else {
				cHolder = (ChildViewHolder) convertView.getTag();
			}
			String description = feed.getItem(groupPosition).getDescription();
			cHolder.tv_content.setText(description + "\n\n"
					+ getResources().getString(R.string.rss_details_link)
					+ "\n" + feed.getItem(groupPosition).getLink());
			cHolder.btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.putExtra("lurl", feed.getItem(groupPosition)
							.getLink());
					intent.setClass(RssExpandActivity.this,
							RssWebActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.scale_rotate,
							R.anim.my_alpha_action);
				}
			});
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.v(this.getClass().getName(), "requestCode:" + requestCode
				+ ",resultCode:" + resultCode + ",Intent:" + data);
		super.onActivityResult(requestCode, resultCode, data);
	}

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