package com.blueskyconnie.mymapapp;

import java.util.Arrays;

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
import com.blueskyconnie.mymapapp.data.Course.APPTYPE;
import com.blueskyconnie.mymapapp.data.Course.STATUS;
import com.blueskyconnie.mymapapp.data.CourseAdapter;

public class CourseTakenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_taken);

		ListView lv = (ListView) this.findViewById(R.id.lstviewTaken);
		CourseAdapter adapter = new CourseAdapter(
				Arrays.asList(new Course("Certificate in Google Android Application Development for Marketing Managers", 
									APPTYPE.ANDROID, STATUS.TAKEN, "ANDROIDAIO"),
						new Course("Certificate in Google Android Mobile and Tablet Application Development", 
								APPTYPE.ANDROID, STATUS.TAKEN, "ANDROIDAPP")), this);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				RelativeLayout relLayout = (RelativeLayout) view;
				TextView tv = (TextView) relLayout.getChildAt(1);
				Toast.makeText(CourseTakenActivity.this, tv.getText(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.course_taken, menu);
		return true;
	}

}
