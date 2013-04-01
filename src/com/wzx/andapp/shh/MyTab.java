package com.wzx.andapp.shh;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class MyTab extends TabActivity implements OnTabChangeListener {

	private TabHost myTabhost;
	protected int myMenuSettingTag = 0;
	protected Menu myMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myTabhost = this.getTabHost();
		// get Tabhost
		LayoutInflater.from(this).inflate(R.layout.tabmain,
				myTabhost.getTabContentView(), true);
		myTabhost.setBackgroundColor(Color.argb(150, 22, 70, 150));






		
		
		myTabhost.addTab(myTabhost.newTabSpec("un")// make a new Tab
				.setIndicator("MMS", getResources().getDrawable(R.drawable.gimp))
				// set the Title and Icon
				// .setContent(R.id.widget_layout_red));
				.setContent(new Intent(this, MmsListActivity.class)));
		myTabhost.setOnTabChangedListener(this);
		


		
		myTabhost.setCurrentTabByTag("un");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// Hold on to this
		myMenu = menu;
		myMenu.clear();
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onTabChanged(String tagString) {
		
		if (myMenu != null) {
			onCreateOptionsMenu(myMenu);
		}
	}

}
