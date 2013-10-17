package com.blueskyconnie.mymapapp.data;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blueskyconnie.mymapapp.R;

public class CourseAdapter extends ArrayAdapter<Course> {

	private List<Course> courseList;
	private Context context;
	private CourseDataSource datasource;
	
	public CourseAdapter(List<Course> courseList, Context context,
			CourseDataSource datasource) {
		super(context, R.layout.custom_row_layout, courseList);
		this.courseList = courseList;
		this.context = context;
		this.datasource = datasource;
	}

	public int getCount() {
		return courseList.size();
	}

	public Course getItem(int position) {
		if (courseList.size() > position) {
			return courseList.get(position);
		}
		return null;
	}

	public long getItemId(int position) {
		Course c = getItem(position);
		return (c != null ? c.hashCode() : -1);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		CourseHolder holder = new CourseHolder();
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.custom_row_layout, null);
			TextView tmpTV = (TextView)v.findViewById(R.id.tvCourseName);
			ImageView tmpIV = (ImageView) v.findViewById(R.id.imgview);
			ImageButton btnEdit = (ImageButton) v.findViewById(R.id.imgbtnEdit);
			ImageButton btnDelete = (ImageButton) v.findViewById(R.id.imgbtnDelete);
						 
			holder.tvCourse = tmpTV;
			holder.imgView = tmpIV;
			holder.tvInstructor = (TextView) v.findViewById(R.id.tvInstructor);
			holder.imgBtnDelete = btnDelete;
			holder.imgBtnEdit = btnEdit;
			v.setTag(holder);
		} else {
			holder = (CourseHolder) v.getTag();
		}
		
		final Course course = this.getItem(position);
		if (course != null) {
			holder.tvCourse.setText(course.getCourseName());
			int imgId = R.drawable.img_question;
			switch (course.getCourseType()) {
				case ANDROID:
					imgId = R.drawable.img_android;
					break;
				case IOS:
					imgId = R.drawable.img_apple;
					break;
				case WINS:
					imgId = R.drawable.img_win;
					break;
			}
			holder.imgView.setImageResource(imgId);
			holder.tvInstructor.setText(course.getInstructor());
			
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					switch (v.getId()) {
						case R.id.imgbtnDelete:
							CourseAdapter.this.createConfirmDeleteDialog(course).show();
							break;
						case R.id.imgbtnEdit:
							CourseAdapter.this.createEditCourseDialog(course).show();
							break;
					}
				}
			};
			
			holder.imgBtnDelete.setOnClickListener(listener);
			holder.imgBtnEdit.setOnClickListener(listener);
		}
		return v;
	}

	private Dialog createConfirmDeleteDialog(final Course course) {
		
		DialogInterface.OnClickListener diagListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
					case AlertDialog.BUTTON_NEGATIVE:
						dialog.dismiss();
						break;
					case AlertDialog.BUTTON_POSITIVE:
						if (datasource.deleteCourse(course)) {
							CourseAdapter.this.remove(course);
							CourseAdapter.this.notifyDataSetChanged();
							Toast.makeText(context, context.getString(R.string.course_deleted), Toast.LENGTH_SHORT).show();
						}
						dialog.dismiss();
						break;
				}
			}
		};
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(context.getString(R.string.title_confirm));
		builder.setMessage(context.getString(R.string.confirmDelete));
		builder.setPositiveButton(R.string.btn_ok, diagListener);
		builder.setNegativeButton(R.string.btn_cancel, diagListener);
		return builder.create();
	}
	
	private Dialog createEditCourseDialog(final Course course) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View editCourseView = inflater.inflate(R.layout.edit_course_layout, null);
		final RadioGroup radGrpType = (RadioGroup) editCourseView.findViewById(R.id.radCourseType);
		final Button btnCancel = (Button) editCourseView.findViewById(R.id.btnEditCancel);
		final Button btnSave = (Button) editCourseView.findViewById(R.id.btnEditSave);
		final TextView tvCourseName = (TextView) editCourseView.findViewById(R.id.tvCourseName);
		final EditText editInstructor = (EditText) editCourseView.findViewById(R.id.editInstructor);
		editInstructor.setText(course.getInstructor());
		tvCourseName.setText(course.getCourseName());
		
		// populate radio group
		Course.APPTYPE[] arrTypes = Course.APPTYPE.values();
		for (Course.APPTYPE appType : arrTypes) {
			RadioButton rb = new RadioButton(context);
			rb.setText(appType.name());
			radGrpType.addView(rb);
			if (appType.name().equals(course.getCourseType().name())) {
				rb.setChecked(true);
			}
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(context.getString(R.string.edit_course_title));
		builder.setView(editCourseView);
		final AlertDialog dialog = builder.create();
		
		View.OnClickListener editListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				switch (v.getId()) {
					case R.id.btnEditCancel:
						dialog.dismiss();
						break;
					case R.id.btnEditSave:
						String instructor = editInstructor.getText().toString();
						int radioButtonID = radGrpType.getCheckedRadioButtonId();
						RadioButton rb = (RadioButton)radGrpType.findViewById(radioButtonID);
						Course.APPTYPE appType = Course.APPTYPE.valueOf(rb.getText().toString());
						if (datasource.updateCourse(course, instructor, appType)) {
							course.setInstructor(instructor);
							course.setCourseType(appType);
							CourseAdapter.this.notifyDataSetChanged();
							Toast.makeText(context, "Course updated.", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(context, "No course is updated.", Toast.LENGTH_SHORT).show();
						}
						dialog.dismiss();
						break;
				}
			}
		};
		btnCancel.setOnClickListener(editListener);
		btnSave.setOnClickListener(editListener);
		return dialog;
	}
	
	private static class CourseHolder {
		TextView tvCourse;
		ImageView imgView;
		TextView tvInstructor;
		ImageButton imgBtnEdit;
		ImageButton imgBtnDelete;
	}
}
