package com.wzx.andapp.shh;

//download by http://www.codefans.net
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class InputCmdActivity extends Activity {
	private static final String TAG = "System";

	ListView itemlist = null;
	List<Map<String, Object>> list;
	EditText cmd_etext = null;
	EditText folder_etext = null;
	Button query_btn = null;
	private String folder = "/system/bin/";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_sys);
		setTitle("系统信息");
		cmd_etext = (EditText) findViewById(R.id.cmd_etext);
//		folder_etext = (EditText) findViewById(R.id.folder_etext);
		query_btn = (Button) findViewById(R.id.query_btn);
		query_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				String folder = folder_etext.getText().toString();
				String cmd = cmd_etext.getText().toString();
				Intent intent = new Intent();
				intent.putExtra("folder", folder);
				intent.putExtra("cmds", cmd);
				intent.setClass(InputCmdActivity.this, SysQueryActivity.class);
				startActivity(intent);
			}
		});
	}
}
