package com.blueskyconnie.mymapapp;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;


@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity  {

	private TabHost mTabHost;
	private TabHost.TabSpec tabSpecBranch;
	private TabHost.TabSpec tabSpecAboutMe;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
		
		tabSpecBranch = mTabHost.newTabSpec("branchTab").setIndicator(this.getString(R.string.branch_tab_indicator));
		tabSpecBranch.setContent(new Intent(this, BranchActivity.class));

		tabSpecAboutMe = mTabHost.newTabSpec("aboutMeTab").setIndicator(this.getString(R.string.aboutme_tab_indicator));
		tabSpecAboutMe.setContent(new Intent(this, AboutUsActivity.class));
		
		mTabHost.addTab(tabSpecBranch);
		mTabHost.addTab(tabSpecAboutMe);
		mTabHost.getTabWidget().setCurrentTab(0);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}


