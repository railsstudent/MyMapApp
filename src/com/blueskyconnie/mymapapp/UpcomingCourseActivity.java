package com.blueskyconnie.mymapapp;

import java.util.Arrays;

import com.blueskyconnie.mymapapp.data.Course;
import com.blueskyconnie.mymapapp.data.CourseAdapter;
import com.blueskyconnie.mymapapp.data.Course.APPTYPE;
import com.blueskyconnie.mymapapp.data.Course.STATUS;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class UpcomingCourseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upcoming_course);
		
		ListView lv = (ListView) this.findViewById(R.id.lstViewUpcoming);
		CourseAdapter adapter = new CourseAdapter(
				Arrays.asList(new Course("Certificate in iPhone & iPad Application Development for Marketing Managers", 
									APPTYPE.IOS, STATUS.UPCOMING, "IPHONEAPPS"),
						new Course("Certificate in iPhone Application Development", 
								APPTYPE.IOS, STATUS.UPCOMING, "IPHONEDEV"),			
						new Course("Certificate in iPad Application Development", 
								APPTYPE.IOS, STATUS.UPCOMING, "IPADDEV")), this);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				RelativeLayout relLayout = (RelativeLayout) view;
				TextView tv = (TextView) relLayout.getChildAt(1);
				Toast.makeText(UpcomingCourseActivity.this, tv.getText(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.upcoming_course, menu);
		return true;
	}

}
