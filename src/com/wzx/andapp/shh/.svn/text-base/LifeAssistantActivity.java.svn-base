package com.wzx.andapp.shh;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

public class LifeAssistantActivity extends TabActivity implements
		OnTabChangeListener {
	/** Called when the activity is first created. */
	private TabHost myTabhost;
	protected int myMenuSettingTag = 0;
	protected Menu myMenu;
	private static final int myMenuResources[] = { R.menu.a_menu,
			R.menu.b_menu, R.menu.c_menu };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = new Intent();
		intent.setClass(LifeAssistantActivity.this,InfosAssistant.class);
		myTabhost = this.getTabHost();
		// get Tabhost
		LayoutInflater.from(this).inflate(R.layout.main,
				myTabhost.getTabContentView(), true);
		myTabhost.setBackgroundColor(Color.argb(150, 22, 70, 150));

		myTabhost.addTab(myTabhost.newTabSpec("One")// make a new Tab
				.setIndicator("A", getResources().getDrawable(R.drawable.gimp))
				// set the Title and Icon
				.setContent(R.id.widget_layout_Blue));
		// set the layout

		myTabhost.addTab(myTabhost
				.newTabSpec("Two")
				// make a new Tab
				.setIndicator("B",
						getResources().getDrawable(R.drawable.mumule))
				// set the Title and Icon
				.setContent(R.id.widget_layout_red));
		// set the layout

		myTabhost.addTab(myTabhost
				.newTabSpec("Three")
				// make a new Tab
				.setIndicator("C",
						getResources().getDrawable(R.drawable.notepad))
				// set the Title and Icon
						.setContent(intent));		
//				.setContent(R.id.widget_layout_red));
		// set the layout

		myTabhost.setOnTabChangedListener(this);

		// /**
		// TabHost tabHost = getTabHost();
		// LayoutInflater.from(this).inflate(R.layout.tab,
		// tabHost.getTabContentView(), true);
		// tabHost.addTab(tabHost.newTabSpec("biaoqian1").setIndicator("±Í«©tab1")
		// .setContent(R.id.tab1));
		// tabHost.addTab(tabHost.newTabSpec("biaoqian2").setIndicator("±Í«©tab2")
		// .setContent(R.id.tab2));
		// tabHost.addTab(tabHost.newTabSpec("biaoqian3").setIndicator("±Í«©tab3")
		// .setContent(R.id.tab3));
		// */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// Hold on to this
		myMenu = menu;
		myMenu.clear();
		// Inflate the currently selected menu XML resource.
		MenuInflater inflater = getMenuInflater();
		switch (myMenuSettingTag) {
		case 1:
			inflater.inflate(myMenuResources[0], menu);
			break;
		case 2:
			inflater.inflate(myMenuResources[1], menu);
			break;
		case 3:
			inflater.inflate(myMenuResources[2], menu);
			break;
		default:
			inflater.inflate(myMenuResources[0], menu);
			break;
		}
		Toast.makeText(this, "onCreateOptionsMenu=" + menu, Toast.LENGTH_LONG)
				.show();
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onTabChanged(String tagString) {
		// TODO Auto-generated method stub
		if (tagString.equals("One")) {
			myMenuSettingTag = 1;
		}
		if (tagString.equals("Two")) {
			myMenuSettingTag = 2;
		}
		if (tagString.equals("Three")) {
			myMenuSettingTag = 3;
		}
		// if (myMenu != null) {
		// onCreateOptionsMenu(myMenu);
		// }
		Toast.makeText(this, "onTabChanged=" + tagString, Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast.makeText(this, "onOptionsItemSelected=" + item.getItemId(),
				Toast.LENGTH_LONG).show();
		switch (item.getItemId()) {
		case R.menu.a_menu:
			setTitle("a_menu");
			break;
		case R.menu.b_menu:
			setTitle("b_menu");
			break;
		case R.menu.c_menu:
			setTitle("c_menu");
			break;
		default:
			setTitle("a_menu");
			break;
		}
		return true;
	}
}