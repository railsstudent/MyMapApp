package com.blueskyconnie.mymapapp;


import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity  {

	private static final String CURRENT_TAB = "currentTab";
	private static final String TAKEN_TAB = "takenTab";
	private static final String UPCOMING_TAB = "upcomingTab";

	private TabHost mTabHost;
	private TabHost.TabSpec tabSpecBranch;
	private TabHost.TabSpec tabSpecAboutMe;
	private TabHost.TabSpec tabSpecCourseTaken;
	private TabHost.TabSpec tabSpecCurrentCourse;
	private TabHost.TabSpec tabSpecUpcomingCourse;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
		
		tabSpecBranch = mTabHost.newTabSpec("branchTab").setIndicator(this.getString(R.string.branch_tab_indicator));
		tabSpecBranch.setContent(new Intent(this, BranchActivity.class));

		tabSpecAboutMe = mTabHost.newTabSpec("aboutMeTab").setIndicator(this.getString(R.string.aboutme_tab_indicator));
		tabSpecAboutMe.setContent(new Intent(this, AboutUsActivity.class));
	
		tabSpecCourseTaken = mTabHost.newTabSpec(TAKEN_TAB).setIndicator(this.getString(R.string.taken_tab_indicator));
		tabSpecCourseTaken.setContent(new Intent(this, CourseTakenActivity.class));
	
		tabSpecCurrentCourse = mTabHost.newTabSpec(CURRENT_TAB).setIndicator(this.getString(R.string.current_tab_indicator));
		tabSpecCurrentCourse.setContent(new Intent(this, CurrentCourseActivity.class));
	
		tabSpecUpcomingCourse = mTabHost.newTabSpec(UPCOMING_TAB).setIndicator(this.getString(R.string.upcoming_tab_indicator));
		tabSpecUpcomingCourse.setContent(new Intent(this, UpcomingCourseActivity.class));

		mTabHost.addTab(tabSpecCurrentCourse);
		mTabHost.addTab(tabSpecUpcomingCourse);
		mTabHost.addTab(tabSpecCourseTaken);
		mTabHost.addTab(tabSpecBranch);
		mTabHost.addTab(tabSpecAboutMe);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		String currentTab = mTabHost.getCurrentTabTag();
		SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
		editor.putString(CURRENT_TAB, currentTab);
		editor.commit();
	}

	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences mypref = this.getPreferences(Context.MODE_PRIVATE);
		String currentTab = mypref.getString(CURRENT_TAB, CURRENT_TAB);
		mTabHost.setCurrentTabByTag(currentTab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}


