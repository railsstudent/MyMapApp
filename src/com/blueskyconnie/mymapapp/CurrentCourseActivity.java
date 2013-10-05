package com.blueskyconnie.mymapapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blueskyconnie.mymapapp.data.Course;
import com.blueskyconnie.mymapapp.data.CourseAdapter;
import com.blueskyconnie.mymapapp.data.CourseDataSource;
import com.blueskyconnie.mymapapp.uihelper.Utility;

public class CurrentCourseActivity extends ListActivity {

	private CourseDataSource datasource = null;
	private CourseAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current_course);
		this.registerForContextMenu(this.getListView());
	}
	
	protected void onListItemClick(ListView l, View view, int position, long id) {
		RelativeLayout relLayout = (RelativeLayout) view;
		LinearLayout linLayout = (LinearLayout) relLayout.getChildAt(2);
		TextView tvCode = (TextView) linLayout.getChildAt(0);
		Intent it = new Intent(this, CourseWebViewActivity.class);
		it.putExtra("code", tvCode.getText());
		this.startActivity(it);

	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.current_course, menu);
		return true;
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
	    this.getMenuInflater().inflate(R.menu.menu_context_current, menu);
		menu.setHeaderTitle(this.getString(R.string.actions));
	}

	public boolean onContextItemSelected(MenuItem item) {
//		AdapterContextMenuInfo adpInfo = (AdapterContextMenuInfo) item.getMenuInfo();
//		CourseAdapter adapter = (CourseAdapter) getListView().getAdapter();
//		Course course = (Course) adapter.getItem(adpInfo.position);
		// change to upcoming
		if (item.getItemId() == R.id.menu_upcoming) {
			Utility.handleContextMenuItemClicked(this, item, this.getListView(), datasource, Course.STATUS.UPCOMING);
//			if (course != null) {
//				if (datasource.updateStatus(course, Course.STATUS.UPCOMING)) {
//					adapter.remove(course);
//					adapter.notifyDataSetChanged();
//					Toast.makeText(this, "Course status of " + course.getCourseName() + " updated to Upcoming.", 
//						Toast.LENGTH_SHORT).show();
//				}
//			}
		} else if (item.getItemId() == R.id.menu_taken) {
			Utility.handleContextMenuItemClicked(this, item, this.getListView(), datasource, Course.STATUS.TAKEN);
//			if (course != null) {
//				if (datasource.updateStatus(course, Course.STATUS.TAKEN)) {
//					adapter.remove(course);
//					adapter.notifyDataSetChanged();
//					Toast.makeText(this, "Course status of " + course.getCourseName() + " updated to Taken.", 
//						Toast.LENGTH_SHORT).show();
//				}
//			}
		} else if (item.getItemId() == R.id.menu_delete) {
			Toast.makeText(this, "You clicked " + item.getTitle(), Toast.LENGTH_SHORT).show();
		} else if (item.getItemId() == R.id.menu_edit) {
			Toast.makeText(this, "You clicked " + item.getTitle(), Toast.LENGTH_SHORT).show();
		} 
		return super.onContextItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		datasource = new CourseDataSource(this);
		adapter = new CourseAdapter(datasource.getCourses(Course.STATUS.CURRENT), this);
		this.setListAdapter(adapter);
	}
}
