package com.blueskyconnie.mymapapp;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blueskyconnie.mymapapp.data.Course;
import com.blueskyconnie.mymapapp.data.CourseAdapter;

public class CurrentCourseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current_course);
		
		ListView lv = (ListView) this.findViewById(R.id.lstViewCurrent);
		CourseAdapter adapter = new CourseAdapter(new ArrayList<Course>(), this);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				RelativeLayout relLayout = (RelativeLayout) view;
				TextView tv = (TextView) relLayout.getChildAt(1);
				Toast.makeText(CurrentCourseActivity.this, tv.getText(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.current_course, menu);
		return true;
	}

}
