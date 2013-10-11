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

import com.blueskyconnie.mymapapp.data.Course;
import com.blueskyconnie.mymapapp.data.CourseAdapter;
import com.blueskyconnie.mymapapp.data.CourseDataSource;
import com.blueskyconnie.mymapapp.helper.DialogHelper;
import com.blueskyconnie.mymapapp.helper.UIController;

public class UpcomingCourseActivity extends ListActivity {

	private CourseDataSource datasource = null;
	private CourseAdapter adapter;
	private UIController controller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upcoming_course);
		// register context menu to list view
		this.registerForContextMenu(this.getListView());
		controller = new UIController();
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
		return true;
	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		this.getMenuInflater().inflate(R.menu.menu_context_upcoming, menu);
		menu.setHeaderTitle(this.getString(R.string.actions));
	}

	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_current) {
			controller.handleContextMenuItemClicked(this, item, this.getListView(), 
					datasource, Course.STATUS.CURRENT);
		} else if (item.getItemId() == R.id.menu_delete) {
			DialogHelper.createConfirmDeleteDialog(this, item, getListView(), datasource).show();
		} else if (item.getItemId() == R.id.menu_edit) {
//			Toast.makeText(this, "You clicked " + item.getTitle(), Toast.LENGTH_SHORT).show();
			DialogHelper.createEditCourseDialog(this, item, getListView(), datasource).show();
		}
		return super.onContextItemSelected(item);
	}

//	@Override
//	protected Dialog onCreateDialog(int id) {
//		Dialog dialog = null;
//		switch (id) {
//			case DialogHelper.DIALOG_CONFIRM_DELETE:
//				dialog = DialogHelper.createConfirmDeleteDialog(this, "Confirm", datasource, );
//				break;
//		}
//		return dialog;
//	} 

	protected void onResume() {
		super.onResume();
		datasource = new CourseDataSource(this);
		adapter = new CourseAdapter(datasource.getCourses(Course.STATUS.UPCOMING), this);
		this.setListAdapter(adapter);
	}
}
