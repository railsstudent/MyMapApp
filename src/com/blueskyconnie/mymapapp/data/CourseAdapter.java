package com.blueskyconnie.mymapapp.data;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blueskyconnie.mymapapp.R;

public class CourseAdapter extends ArrayAdapter<Course> {

	private List<Course> courseList;
	private Context context;
	
	public CourseAdapter(List<Course> courseList, Context context) {
		super(context, R.layout.custom_row_layout, courseList);
		this.courseList = courseList;
		this.context = context;
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
						
			holder.tvCourse = tmpTV;
			holder.imgView = tmpIV;
			holder.tvCode = (TextView) v.findViewById(R.id.tvCode);
			holder.tvInstructor = (TextView) v.findViewById(R.id.tvInstructor);
			v.setTag(holder);
		} else {
			holder = (CourseHolder) v.getTag();
		}
		
		Course course = this.getItem(position);
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
			holder.tvCode.setText(course.getCode());
			holder.tvInstructor.setText(course.getInstructor());
		}
		return v;
	}

	private static class CourseHolder {
		TextView tvCourse;
		ImageView imgView;
		TextView tvCode;
		TextView tvInstructor;
	}
}
