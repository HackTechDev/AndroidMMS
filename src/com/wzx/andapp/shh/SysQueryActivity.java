package com.wzx.andapp.shh;

//download by http://www.codefans.net

import com.wzx.andapp.assists.FetchData;
import com.wzx.andapp.assists.PreferencesUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class SysQueryActivity extends Activity implements Runnable {
	private static final String TAG = "ShowInfo";

	TextView info;
	TextView title;
	private ProgressDialog pd;
	public String info_datas;
	public boolean is_valid = false;
	public int _id = 0;
	public String _name = "";
	public int _position = 0;
	public int _ref = 0;

	String cmd = null;
	String folder = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showinfo);

		Intent intent = getIntent();
		folder = intent.getStringExtra("folder");
		cmd = intent.getStringExtra("cmds");
		// revParams();
		info = (TextView) findViewById(R.id.info);
		title = (TextView) findViewById(R.id.title);
		setTitle("SysQueryActivity: " + _name);
		title.setText(_name);
		load_data();
	}

	private void load_data() {
		pd = ProgressDialog.show(this, "Please Wait a moment..",
				"fetch info datas...", true, true);
		Thread thread = new Thread(this);
		thread.start();
	}

	// 接收传递进来的信息
	private void revParams() {
		Log.i(TAG, "revParams.");
		Intent startingIntent = getIntent();
		if (startingIntent != null) {
			Bundle infod = startingIntent
					.getBundleExtra("android.intent.extra.info");
			if (infod == null) {
				is_valid = false;
			} else {
				_id = infod.getInt("id");
				_name = infod.getString("name");
				_position = infod.getInt("position");
				is_valid = true;
			}
		} else {
			is_valid = false;
		}
		Log.i(TAG, "_name:" + _name + ",_id=" + _id);
	}

	@Override
	public void run() {
		if (_ref > 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Log.i("load_data", "e=" + e.toString());
			}
		}
		info_datas = FetchData.exeCmd(cmd, folder);
		handler.sendEmptyMessage(0);
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			pd.dismiss();
			info.setText(info_datas);
		}
	};

}
