package com.blueskyconnie.mymapapp.data;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CourseAdapter extends ArrayAdapter<Course> {

	
	public CourseAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	private static class CourseHolder {
		TextView tvCourse;
		ImageView imgView;
	}
}
