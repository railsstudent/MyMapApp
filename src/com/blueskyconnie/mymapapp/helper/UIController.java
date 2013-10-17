package com.blueskyconnie.mymapapp.helper;

import android.content.Context;
import android.view.MenuItem;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import com.blueskyconnie.mymapapp.data.Course;
import com.blueskyconnie.mymapapp.data.CourseAdapter;
import com.blueskyconnie.mymapapp.data.CourseDataSource;

public class UIController {

	public void handleContextMenuItemClicked(Context context,
			MenuItem item, ListView view, CourseDataSource datasource,
			Course.STATUS newStatus) {
	
		AdapterContextMenuInfo adpInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		CourseAdapter adapter = (CourseAdapter) view.getAdapter();
		Course course = (Course) adapter.getItem(adpInfo.position);
		if (course != null) {
			if (datasource.updateStatus(course, newStatus)) {
				adapter.remove(course);
				adapter.notifyDataSetChanged();
				Toast.makeText(context, "Course status updated to " + newStatus.getDesc() + ".", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
