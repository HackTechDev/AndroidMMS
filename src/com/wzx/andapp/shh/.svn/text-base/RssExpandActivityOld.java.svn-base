package com.wzx.andapp.shh;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.wzx.andapp.rss.bo.RSSFeed;
import com.wzx.andapp.rss.sax.RSSHandler;

import android.view.View.OnClickListener;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class RssExpandActivityOld extends Activity {
	public final static int MENU_ADD = Menu.FIRST;
	public final static int MENU_DELETE = Menu.FIRST + 1;

	Activity activity;

	List<String> group;
	List<List<String>> child;

	ExpandableListView expandList;
	InfoDetailsAdapter mAdapter;

	Dialog dialogAdd;
	Dialog dialogDelete;

	View viewAdd;
	View viewDelete;

	OnClickListener clickListener;

	EditText add_name;
	EditText add_phone;
	EditText add_sex;
	EditText add_home;
	Button add_ok, add_no;

	EditText delete_id;
	Button delete_ok, delete_no;

	public final String RSS_URL = "http://rss.news.sohu.com/rss/guonei.xml";
	private RSSFeed feed = null;

	private RSSFeed getFeed(String urlString) {
		try {
			URL url = new URL(urlString);

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader xmlreader = parser.getXMLReader();

			RSSHandler rssHandler = new RSSHandler();
			xmlreader.setContentHandler(rssHandler);

			InputSource is = new InputSource(url.openStream());
			xmlreader.parse(is);

			return rssHandler.getFeed();
		} catch (Exception ee) {
			ee.printStackTrace();
			return null;
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expand_list_main);
		feed = getFeed(RSS_URL);

		activity = this;

		// createDialogAdd();
		// createDialogDelete();

		expandList = (ExpandableListView) findViewById(R.id.expandList);

		initialData();

		mAdapter = new InfoDetailsAdapter(this);

		expandList.setAdapter(mAdapter);

		expandList.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, "[Group Click]:" + arg2,
						Toast.LENGTH_LONG).show();

				return false;
			}

		});

		expandList.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1,
					int arg2, int arg3, long arg4) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, "[Child Click]:" + arg2 + ":" + arg3,
						Toast.LENGTH_LONG).show();

				return false;
			}

		});

	}

	// public void createDialogAdd(){
	// viewAdd = this.getLayoutInflater().inflate(R.layout.add, null);
	//
	// dialogAdd = new Dialog(this);
	// dialogAdd.setContentView(viewAdd);
	// dialogAdd.setTitle("输入新成员信息");
	//
	// add_name = (EditText)viewAdd.findViewById(R.id.add_name);
	// add_phone = (EditText)viewAdd.findViewById(R.id.add_phone);
	// add_sex = (EditText)viewAdd.findViewById(R.id.add_sex);
	// add_home = (EditText)viewAdd.findViewById(R.id.add_home);
	//
	// add_ok = (Button)viewAdd.findViewById(R.id.add_ok);
	// add_no = (Button)viewAdd.findViewById(R.id.add_no);
	//
	// add_ok.setOnClickListener(new OnClickListener(){
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// String[] data = {
	// add_phone.getText().toString(),
	// add_sex.getText().toString(),
	// add_home.getText().toString()
	// };
	//
	// addInfo(add_name.getText().toString(),data);
	//
	// dialogAdd.dismiss();
	//
	// mAdapter.notifyDataSetChanged();
	// }
	// });
	//
	// add_no.setOnClickListener(new OnClickListener(){
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// dialogAdd.dismiss();
	// }
	// });
	// }

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

	public void initialData() {
		group = new ArrayList<String>();

		child = new ArrayList<List<String>>();

		addInfo("griffinshi", new String[] { "13776117119", "man", "Jiangsu" });
		addInfo("lancewu", new String[] { "1321134", "man", "Taiwan" });
		addInfo("kandyli", new String[] { "12345" });
	}

	public void addInfo(String p, String[] c) {
		group.add(p);

		List<String> item = new ArrayList<String>();

		for (int i = 0; i < c.length; i++) {
			item.add(c[i]);
		}

		child.add(item);
	}

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

	public class InfoDetailsAdapter extends BaseExpandableListAdapter {
		Activity activity;

		public InfoDetailsAdapter(Activity a) {
			activity = a;
		}

		// child method stub

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return child.get(groupPosition).get(childPosition);
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
			return getGenericChildView(groupPosition, childPosition);
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
			return getGenericGroupView(groupPosition);
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

		public View getGenericGroupView(int groupPosition) {
			// Layout parameters for the ExpandableListView
			View view = View.inflate(RssExpandActivityOld.this,
					R.layout.rss_itemrow, null);
			TextView tv_title = (TextView) view.findViewById(R.id.title);
			String title = feed.getItem(groupPosition).getTitle();
			tv_title.setText(title);
			TextView tv_pubdate = (TextView) view.findViewById(R.id.pubdate);
			String pubDate = feed.getItem(groupPosition).getPubDate();
			tv_pubdate.setText(pubDate);
			return view;
		}

		public View getGenericChildView(int groupPosition, int childPosition) {

			View view = View.inflate(RssExpandActivityOld.this,
					R.layout.rss_itemdetail, null);
			TextView tv_content = (TextView) view.findViewById(R.id.content);
			String description = feed.getItem(groupPosition).getDescription();
			tv_content.setText(description + "\n\n"
					+ getResources().getString(R.string.rss_details_link)
					+ "\n" + feed.getItem(groupPosition).getLink());
			return view;
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

}