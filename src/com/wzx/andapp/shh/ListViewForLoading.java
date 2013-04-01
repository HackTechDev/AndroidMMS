package com.wzx.andapp.shh;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout.LayoutParams;

public class ListViewForLoading extends Activity implements OnScrollListener {

	private SimpleAdapter adapter;// = new listViewAdapter();
	ListView listView;
	LinearLayout loadingLayout;
	private int getLastVisiblePosition = 0, lastVisiblePositionY = 0;
	ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
	private Thread mThread;
	/**
	 * 设置布局显示属性
	 */
	private LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);
	/**
	 * 设置布局显示目标最大化属性
	 */
	private LayoutParams FFlayoutParams = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.FILL_PARENT,
			LinearLayout.LayoutParams.FILL_PARENT);

	private ProgressBar progressBar;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_rss_links);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		// 线性布局
		LinearLayout layout = new LinearLayout(this);
		// 设置布局 水平方向
		layout.setOrientation(LinearLayout.HORIZONTAL);
		// 进度条
		progressBar = new ProgressBar(this);
		// 进度条显示位置
		progressBar.setPadding(0, 0, 15, 0);
		// 把进度条加入到layout中
		layout.addView(progressBar, mLayoutParams);
		// 文本内容
		TextView textView = new TextView(this);
		textView.setText("加载中...");
		textView.setGravity(Gravity.CENTER_VERTICAL);
		// 把文本加入到layout中
		layout.addView(textView, FFlayoutParams);
		// 设置layout的重力方向，即对齐方式是
		layout.setGravity(Gravity.CENTER);

		// 设置ListView的页脚layout
		loadingLayout = new LinearLayout(this);
		loadingLayout.addView(layout, mLayoutParams);
		loadingLayout.setGravity(Gravity.CENTER);

		// 得到一个ListView用来显示条目
		listView = (ListView) findViewById(R.id.ListView01);
		// 添加到脚页显示
		listView.addFooterView(loadingLayout);
		// 给ListView添加适配器
		
		for (int i = 0; i < 5; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();

			map.put("ItemImage", R.drawable.checked);
			map.put("ItemTitle", String.valueOf(i) + "个人信息");
			map.put("LastImage", R.drawable.lastimage);
			map.put("_id", i);
			listItem.add(map);
		}
		
		adapter = new SimpleAdapter(this, listItem,
				R.layout.list_itemrow, new String[] { "ItemImage", "ItemTitle",
						"ItemImage" }, new int[] { R.id.ItemImage,
						R.id.ItemTitle, R.id.last });
		listView.setAdapter(adapter);
		
		// 给ListView注册滚动监听
		listView.setOnScrollListener(this);
	}

	/**
	 * 要用用于生成显示数据
	 * 
	 * @author huangbq
	 * 
	 */
	class listViewAdapter extends BaseAdapter {
		int count = 10;

		public int getCount() {
			return count;
		}

		public Object getItem(int pos) {
			return pos;
		}

		public long getItemId(int pos) {
			return pos;
		}

		public View getView(int pos, View v, ViewGroup p) {
			TextView view;
			if (v == null) {
				view = new TextView(ListViewForLoading.this);
			} else {
				view = (TextView) v;
			}
			view.setText("ListItem " + pos);
			view.setTextSize(20f);
			view.setGravity(Gravity.CENTER);
			view.setHeight(60);
			return view;
		}
	}
	private void loadRemnantListItem() {// 滚到加载余下的数据
		int pos = listView.getCount();
		
		for (int i = pos; i < pos + 5; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();

			map.put("ItemImage", R.drawable.d);
			map.put("ItemTitle", "网络设置" + String.valueOf(i));
			map.put("LastImage", R.drawable.lastimage);
			map.put("_id", i);
			listItem.add(map);

		}
		listView.setSelection(pos-1);
//		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		// if (firstVisibleItem + visibleItemCount == totalItemCount) {
		// // 开线程去下载网络数据
		// if (mThread == null || !mThread.isAlive()) {
		// mThread = new Thread() {
		// @Override
		// public void run() {
		// try {
		// // 这里放你网络数据请求的方法，我在这里用线程休眠5秒方法来处理
		// Thread.sleep(5000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// Message message = new Message();
		// message.what = 1;
		// handler.sendMessage(message);
		// }
		// };
		// mThread.start();
		// }
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
					if (mThread == null || !mThread.isAlive()) {
						mThread = new Thread() {
							@Override
							public void run() {
								try {
									// 这里放你网络数据请求的方法，我在这里用线程休眠5秒方法来处理
									Thread.sleep(5000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							}
						};
						mThread.start();

					}
				}
				// 未滚动到底部，第二次拖至底部都初始化
				getLastVisiblePosition = 0;
				lastVisiblePositionY = 0;
			}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				if (adapter.getCount() <= 41) {
					loadRemnantListItem();
//					adapter.count += 10;
					int currentPage = adapter.getCount() / 10;
					Toast.makeText(getApplicationContext(),
							"第" + currentPage + "页", Toast.LENGTH_LONG).show();
				} else {
					listView.removeFooterView(loadingLayout);
				}
				// 重新刷新Listview的adapter里面数据
				adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
		}

	};
}
