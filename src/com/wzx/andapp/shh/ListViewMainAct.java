package com.wzx.andapp.shh;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ListViewMainAct extends Activity {
	/** Called when the activity is first created. */
	public ListView list;
	public View footView;
	public View headView;
	private int getLastVisiblePosition = 0, lastVisiblePositionY = 0;
	ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
	SimpleAdapter listvSimpleAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_rss_links);
		list = (ListView) findViewById(R.id.ListView01);
//		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 5; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();

			map.put("ItemImage", R.drawable.checked);
			map.put("ItemTitle", String.valueOf(i) + "个人信息");
			map.put("LastImage", R.drawable.lastimage);
			map.put("_id", i);
			listItem.add(map);
		}
		footView = new View(this);
		list.addFooterView(footView);

		headView = new View(this);
		list.addHeaderView(headView);

		listvSimpleAdapter = new SimpleAdapter(this, listItem,
				R.layout.list_itemrow, new String[] { "ItemImage", "ItemTitle",
						"ItemImage" }, new int[] { R.id.ItemImage,
						R.id.ItemTitle, R.id.last });
		list.setAdapter(listvSimpleAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				setTitle("点击第" + arg2 + "个项目");
				if (arg2 == 4) {
					ListViewMainAct.this.finish();
				}
			}
		});
		/**
		 * 长按菜单
		 */
		list.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				menu.setHeaderTitle("长按菜单-ContextMenu");
				java.lang.System.out.println(((ListView) v)
						.getSelectedItemPosition()
						+ " ### "
						+ ((ListView) v).getSelectedItemId());
				menu.add(0, 0, 0, "弹出长按菜单0");
				menu.add(0, 1, 0, "弹出长按菜单1");
			}
		});
		list.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
//				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
//					if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
//						View v = (View) view.getChildAt(view.getChildCount() - 1);
//						int[] location = new int[2];
//						v.getLocationOnScreen(location);// 获取在整个屏幕内的绝对坐标
//						int y = location[1];
//
//						Log.e("x" + location[0], "y" + location[1]);
//						if (view.getLastVisiblePosition() != getLastVisiblePosition
//								&& lastVisiblePositionY != y)// 第一次拖至底部
//						{
//							loadRemnantListItem();
//						}
//					}
//					// 未滚动到底部，第二次拖至底部都初始化
//					getLastVisiblePosition = 0;
//					lastVisiblePositionY = 0;
//				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem + visibleItemCount == totalItemCount) {
					if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
						View v = (View) view.getChildAt(view.getChildCount() - 1);
						int[] location = new int[2];
						v.getLocationOnScreen(location);// 获取在整个屏幕内的绝对坐标
						int y = location[1];

						Log.e("x" + location[0], "y" + location[1]);
						if (view.getLastVisiblePosition() != getLastVisiblePosition
								&& lastVisiblePositionY != y)// 第一次拖至底部
						{
							loadRemnantListItem();
//							list.setSelection(view.getChildCount() - 1);
						}
					}
					// 未滚动到底部，第二次拖至底部都初始化
					getLastVisiblePosition = 0;
					lastVisiblePositionY = 0;
				}
			}

		});

		list.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub
				// if (footView == view) {
				// loadRemnantListItem();
				// list.setSelection(position - 1);
				// } else if (headView == view) {
				// loadRemnantListItem1();
				// list.setSelection(position + 1);
				// }
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

	}

	private void loadRemnantListItem() {// 滚到加载余下的数据
		int pos = list.getCount();
		
		for (int i = pos; i < pos + 5; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();

			map.put("ItemImage", R.drawable.d);
			map.put("ItemTitle", "网络设置" + String.valueOf(i));
			map.put("LastImage", R.drawable.lastimage);
			map.put("_id", i);
			listItem.add(map);

		}
		list.setSelection(pos-1);
//		SimpleAdapter listvSimpleAdapter = new SimpleAdapter(this, listItem,
//				R.layout.list_itemrow, new String[] { "ItemImage", "ItemTitle",
//						"ItemImage" }, new int[] { R.id.ItemImage,
//						R.id.ItemTitle, R.id.last });
//		list.setAdapter(listvSimpleAdapter);
		listvSimpleAdapter.notifyDataSetChanged();
	}

	private void loadRemnantListItem1() {// 滚到加载完的数据
//		list.getAdapter().
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 5; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();

			map.put("ItemImage", R.drawable.checked);
			map.put("ItemTitle", String.valueOf(i) + "个人信息");
			map.put("LastImage", R.drawable.lastimage);
			map.put("_id", i);
			listItem.add(map);

		}

		SimpleAdapter listvSimpleAdapter = new SimpleAdapter(this, listItem,
				R.layout.list_itemrow, new String[] { "ItemImage", "ItemTitle",
						"ItemImage" }, new int[] { R.id.ItemImage,
						R.id.ItemTitle, R.id.last });
		list.setAdapter(listvSimpleAdapter);
	}

	/**
	 * 点击长按菜单出现的效果
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		ContextMenuInfo menuInfo = (ContextMenuInfo) item.getMenuInfo();
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		// int id = (int) info.id;// 这里的info.id对应的就是数据库中_id的值
		// // setTitle("点击了长按菜单里面的第" +
		// // item.getItemId() + "个项目");
		java.lang.System.out.println(info.id + " @@@ " + info.position);

		Toast.makeText(this.getParent(),
				"点击了长按菜单里面的第" + item.getItemId() + "个项目", Toast.LENGTH_LONG)
				.show();
		return super.onContextItemSelected(item);
	}

}