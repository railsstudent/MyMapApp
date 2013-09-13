package com.blueskyconnie.mymapapp;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blueskyconnie.mymapapp.data.Course;
import com.blueskyconnie.mymapapp.data.CourseAdapter;

public class CurrentCourseActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current_course);

		List<Course> lstCourse = new ArrayList<Course>();
		CourseAdapter adapter = new CourseAdapter(lstCourse, this);
		this.setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View view, int position, long id) {
		RelativeLayout relLayout = (RelativeLayout) view;
		LinearLayout linLayout = (LinearLayout) relLayout.getChildAt(2);
		TextView tvCode = (TextView) linLayout.getChildAt(0);
		Intent it = new Intent(this, CourseWebViewActivity.class);
		it.putExtra("code", tvCode.getText());
		this.startActivity(it);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.current_course, menu);
		return true;
	}

}
