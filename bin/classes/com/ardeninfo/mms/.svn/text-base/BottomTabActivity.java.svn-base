package com.wzx.andapp.shh;

//package com.wzx.andapp.activs;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class BottomTabActivity extends ActivityGroup {
	public static TabHost tab_host;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bottomtab);

		tab_host = (TabHost) findViewById(R.id.edit_item_tab_host);
		tab_host.setup(this.getLocalActivityManager());

		TabSpec ts1 = tab_host.newTabSpec("TAB_WEATHER");
		ts1.setIndicator("Weather");
		ts1.setContent(new Intent(this, FSExplorer.class));
		tab_host.addTab(ts1);

		TabSpec ts2 = tab_host.newTabSpec("TAB_MAIL");
		ts2.setIndicator("Mail");
		ts2.setContent(new Intent(this, InfosAssistant.class));
		tab_host.addTab(ts2);

		TabSpec ts3 = tab_host.newTabSpec("TAB_JUMP");
		ts3.setIndicator("Jump");
		ts3.setContent(new Intent(this, InputCmdActivity.class));
		tab_host.addTab(ts3);

		tab_host.setCurrentTab(0);
	}
}