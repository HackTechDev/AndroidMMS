package com.ardeninfo.mms;



import com.ardeninfo.mms.R;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class MyTab extends TabActivity implements OnTabChangeListener {

	private TabHost myTabhost;
	protected int myMenuSettingTag = 0;
	protected Menu myMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		myTabhost = this.getTabHost();

		LayoutInflater.from(this).inflate(R.layout.tabmain,
				myTabhost.getTabContentView(), true);
		myTabhost.setBackgroundColor(Color.argb(150, 22, 70, 150));
		
		myTabhost.addTab(myTabhost.newTabSpec("un")
				.setIndicator("MMS", getResources().getDrawable(R.drawable.gimp))
				.setContent(new Intent(this, MmsListActivity.class)));
		myTabhost.setOnTabChangedListener(this);
		
		myTabhost.setCurrentTabByTag("un");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
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
