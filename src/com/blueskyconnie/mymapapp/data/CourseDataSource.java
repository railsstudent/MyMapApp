package com.blueskyconnie.mymapapp.data;

import java.util.ArrayList;
import java.util.List;

import com.blueskyconnie.mymapapp.data.Course.STATUS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CourseDataSource {

	private CourseSqliteHelper dbHelper;
	private SQLiteDatabase database;
	
	public CourseDataSource(Context context) {
		dbHelper = new CourseSqliteHelper(context);
	}

	public void open() {
		database = dbHelper.getWritableDatabase();
	}
	
	public void openReadonly() {
		database = dbHelper.getReadableDatabase();
	}
	
	public void close() {
		database.close();
	}
	
	// retrieve courses
	public List<Course> getCourses(Course.STATUS status) {
		
		this.openReadonly();
		Cursor cursor = database.query(CourseSqliteHelper.TABLE_COURSE, null, 
				CourseSqliteHelper.COLUMN_STATUS + " = ?", 
				new String[] { status.name() }, null, null, CourseSqliteHelper.COLUMN_NAME + " ASC");
		List<Course> lst = new ArrayList<Course>();
		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				Course course = new Course();
				course.setId(cursor.getInt(0));
				course.setCourseName(cursor.getString(1));
				course.setCourseType(Enum.valueOf(Course.APPTYPE.class, cursor.getString(2).trim()));
				course.setCourseStatus(Enum.valueOf(Course.STATUS.class, cursor.getString(3).trim()));
				course.setCode(cursor.getString(4).trim());
				course.setInstructor(cursor.getString(5).trim());
				lst.add(course);
				cursor.moveToNext();
			}
		}
		this.close();
		return lst;
	}
	
	public synchronized boolean updateStatus(Course course, Course.STATUS status) {
		
		ContentValues values = new ContentValues();
		values.put(CourseSqliteHelper.COLUMN_STATUS, status.name());

		this.open();
		
		int cnt = database.update(CourseSqliteHelper.TABLE_COURSE, values, 
					CourseSqliteHelper.COLUMN_ID + "= ? AND " + 
					CourseSqliteHelper.COLUMN_STATUS + "= ? AND " + 
					CourseSqliteHelper.COLUMN_VER + "= ? ", 
					new String[] { String.valueOf(course.getId()), course.getCourseStatus().name(),
								String.valueOf(course.getVersion()) });
		this.close();
		return cnt > 0;
	}
	
	public synchronized boolean deleteCourse(Course course) {
		this.open();
		int cnt = database.delete(CourseSqliteHelper.TABLE_COURSE, 
				CourseSqliteHelper.COLUMN_ID + "= ? AND " + CourseSqliteHelper.COLUMN_VER + "= ?", 
				new String[] { String.valueOf(course.getId()), String.valueOf(course.getVersion())});
		this.close();
		return cnt > 0;
	}
	
	public long insertCourse(Course newCourse) {
		
		ContentValues values = new ContentValues();
		values.put(CourseSqliteHelper.COLUMN_NAME, newCourse.getCourseName());
		values.put(CourseSqliteHelper.COLUMN_TYPE, newCourse.getCourseType().name());
		values.put(CourseSqliteHelper.COLUMN_STATUS, STATUS.UPCOMING.name());
		values.put(CourseSqliteHelper.COLUMN_CODE, newCourse.getCode());
		values.put(CourseSqliteHelper.COLUMN_INSTRUCTOR, newCourse.getInstructor());

		this.open();
		long newId = database.insert(CourseSqliteHelper.TABLE_COURSE, null, values);
		this.close();
		return newId;
	}
	
	public synchronized boolean updateCourse (Course course, String instructor, 
			Course.APPTYPE appType) {
		
		instructor = instructor == null ? "" : instructor.trim();
		ContentValues values = new ContentValues();
		values.put(CourseSqliteHelper.COLUMN_INSTRUCTOR, instructor);
		values.put(CourseSqliteHelper.COLUMN_TYPE, appType.name());

		this.open();
		int cnt = database.update(CourseSqliteHelper.TABLE_COURSE, values, 
					CourseSqliteHelper.COLUMN_ID + "= ? AND " + CourseSqliteHelper.COLUMN_VER + "= ? ", 
					new String[] { String.valueOf(course.getId()), String.valueOf(course.getVersion()) });
		this.close();
		return cnt > 0;
	}
}
