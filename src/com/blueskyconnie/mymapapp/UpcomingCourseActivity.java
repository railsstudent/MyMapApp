package com.blueskyconnie.mymapapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blueskyconnie.mymapapp.data.Course;
import com.blueskyconnie.mymapapp.data.Course.STATUS;
import com.blueskyconnie.mymapapp.data.CourseAdapter;
import com.blueskyconnie.mymapapp.data.CourseDataSource;
import com.blueskyconnie.mymapapp.helper.UIController;

public class UpcomingCourseActivity extends ListActivity {

	private static final int DIALOG_ADD_COURSE = 1;
	
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
		CourseAdapter adapter = (CourseAdapter) l.getAdapter();
		String code = adapter.getItem(position).getCode();
		Intent it = new Intent(this, CourseWebViewActivity.class);
		it.putExtra("code", code);
		this.startActivity(it);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_current, menu);
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_new) {
			showDialog(DIALOG_ADD_COURSE);
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		final AlertDialog alertDialog = (AlertDialog) dialog;
		switch (id) {
			case DIALOG_ADD_COURSE:
				alertDialog.setTitle(this.getString(R.string.add_new_course));
				Button btnNewCancel = (Button) alertDialog.findViewById(R.id.btnNewCancel);
				Button btnNewCreate = (Button) alertDialog.findViewById(R.id.btnNewCreate);
				TextView tv = (TextView) alertDialog.findViewById(R.id.tvNewStatus);
				tv.setText(Course.STATUS.UPCOMING.name());
				final EditText edtCourse = (EditText) alertDialog.findViewById(R.id.edtNewCourseName);
				final EditText edtCode = (EditText) alertDialog.findViewById(R.id.edtNewCourseCode);
				final EditText edtInstructor = (EditText) alertDialog.findViewById(R.id.edtNewInstructor);
				edtInstructor.setText("NA");

				Spinner spCourseType = (Spinner) alertDialog.findViewById(R.id.spNewCourseType);
				ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
				for (Course.APPTYPE appType : Course.APPTYPE.values()) {
					spinAdapter.add(appType.name());
				}
				spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spCourseType.setAdapter(spinAdapter);
				btnNewCancel.setOnClickListener(new View.OnClickListener(){
					public void onClick(View v) {
						edtCourse.setText("");
						edtCode.setText("");
						edtInstructor.setText("");
						alertDialog.dismiss();
					}
				});
				btnNewCreate.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String name = edtCourse.getText().toString().trim();
						String code = edtCode.getText().toString().trim();
						String instructor = edtInstructor.getText().toString().trim();
						
						if (name == null || name.length() == 0) {
							Toast.makeText(UpcomingCourseActivity.this, 
									UpcomingCourseActivity.this.getString(R.string.blank_course),
									Toast.LENGTH_SHORT).show();
							return;
						}

						if (code == null || code.length() == 0) {
							Toast.makeText(UpcomingCourseActivity.this, 
									UpcomingCourseActivity.this.getString(R.string.blank_code),
									Toast.LENGTH_SHORT).show();
							return;
						}

						if (instructor == null || instructor.length() == 0) {
							Toast.makeText(UpcomingCourseActivity.this, 
									UpcomingCourseActivity.this.getString(R.string.blank_instructor),
									Toast.LENGTH_SHORT).show();
							return;
						}

						Spinner spinner = (Spinner) alertDialog.findViewById(R.id.spNewCourseType);
						int pos = spinner.getSelectedItemPosition();
						String strType = String.valueOf(spinner.getItemAtPosition(pos));
						Course.APPTYPE courseType = Course.APPTYPE.valueOf(strType);
						Course newCourse = new Course();
						newCourse.setCode(code);
						newCourse.setCourseName(name);
						newCourse.setCourseStatus(STATUS.UPCOMING);
						newCourse.setInstructor(instructor);
						newCourse.setCourseType(courseType);
						long newId = datasource.insertCourse(newCourse);
						String msg = "";
						if (newId >= 0) {
							newCourse.setId(Long.valueOf(newId).intValue());
							adapter.add(newCourse);
						} 
						msg = (newId >= 0 ? UpcomingCourseActivity.this.getString(R.string.course_created) : 
											UpcomingCourseActivity.this.getString(R.string.course_not_created));
						adapter.notifyDataSetChanged();
						
						edtCourse.setText("");
						edtCode.setText("");
						edtInstructor.setText("");
						
						alertDialog.dismiss();
						Toast.makeText(UpcomingCourseActivity.this, msg, Toast.LENGTH_SHORT).show();
					}
				});
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (id) {
			case DIALOG_ADD_COURSE:
				View view_add_course = getLayoutInflater().inflate(R.layout.layout_add_course, null);
				builder.setView(view_add_course);
		}
		return builder.create();
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
		}
		return super.onContextItemSelected(item);
	}

	protected void onResume() {
		super.onResume();
		datasource = new CourseDataSource(this);
		adapter = new CourseAdapter(datasource.getCourses(Course.STATUS.UPCOMING), this, datasource);
		this.setListAdapter(adapter);
	}
}
