package com.blueskyconnie.mymapapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.blueskyconnie.mymapapp.data.Course;
import com.blueskyconnie.mymapapp.data.Course.STATUS;
import com.blueskyconnie.mymapapp.data.CourseAdapter;
import com.blueskyconnie.mymapapp.data.CourseDataSource;
import com.blueskyconnie.mymapapp.helper.UIController;

public class CourseTakenActivity extends ListActivity {

	private CourseDataSource datasource = null;
	private UIController controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_taken);
		this.registerForContextMenu(this.getListView());
		controller = new UIController();
	}

	@Override
	protected void onListItemClick(ListView l, View view, int position, long id) {
		CourseAdapter adapter = (CourseAdapter) l.getAdapter();
		String code = adapter.getItem(position).getCode();
		Intent it = new Intent(this, CourseWebViewActivity.class);
		it.putExtra("code", code);
		this.startActivity(it);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.course_taken, menu);
		return true;
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_current) {
			controller.handleContextMenuItemClicked(this, item, this.getListView(), datasource, STATUS.CURRENT);
		} else if (item.getItemId() == R.id.menu_upcoming) {
			controller.handleContextMenuItemClicked(this, item, this.getListView(), datasource, STATUS.UPCOMING);
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.menu_context_taken, menu);
		menu.setHeaderTitle(this.getString(R.string.actions));
	}

	@Override
	protected void onResume() {
		super.onResume();
		datasource = new CourseDataSource(this);
		CourseAdapter adapter = new CourseAdapter(datasource.getCourses(Course.STATUS.TAKEN), this, datasource);
		this.setListAdapter(adapter);
	}
}
