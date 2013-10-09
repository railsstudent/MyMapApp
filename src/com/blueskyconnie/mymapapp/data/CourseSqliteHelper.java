package com.blueskyconnie.mymapapp.data;

import com.blueskyconnie.mymapapp.data.Course.APPTYPE;
import com.blueskyconnie.mymapapp.data.Course.STATUS;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CourseSqliteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "courses.db";
	private static final int DATABASE_VERSION = 1;

	public static final String TABLE_COURSE = "course";
	
	private static final String DATABASE_CREATE = 
			"CREATE TABLE " + TABLE_COURSE + "(id integer primary key autoincrement, " + 
				"name varchar(200) not null, type varchar(50) default '', " + 
				"status varchar(10) default 'UPCOMING', code varchar(50) default '',  " + 
				"instructor varchar(100) default 'TBD');";

	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_STATUS = "status";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_CODE = "code";
	public static final String COLUMN_INSTRUCTOR = "instructor";
		
	public CourseSqliteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		createInitialData(db);
	}

	private void createInitialData(SQLiteDatabase db) {
		// create initial data
		ContentValues values = new ContentValues();
		values.put("name", "Certificate in Google Android Application Development for Marketing Managers");
		values.put("type", APPTYPE.ANDROID.name());
		values.put("status", STATUS.TAKEN.name());
		values.put("code", "ANDROIDAPPS");
		values.put("instructor", "Roger Tang");
		db.insert(TABLE_COURSE, null, values);

		// taken course
		values =  new ContentValues();
		values.put("name", "Certificate in Google Android Mobile and Tablet Application Development");
		values.put("type", APPTYPE.ANDROID.name());
		values.put("status", STATUS.TAKEN.name());
		values.put("code", "ANDROIDAIO");
		values.put("instructor", "Leslie Tsang");
		db.insert(TABLE_COURSE, null, values);
		
		// current courses
		values =  new ContentValues();
		values.put("name", "Certificate in iPhone & iPad Application Development for Marketing Managers");
		values.put("type", APPTYPE.IOS.name());
		values.put("status", STATUS.CURRENT.name());
		values.put("code", "IPHONEAPPS");
		values.put("instructor", "Leslie Tsang");
		db.insert(TABLE_COURSE, null, values);
		
		values =  new ContentValues();
		values.put("name", "Windows 8 & Windows 8 App Development Course");
		values.put("type", APPTYPE.WINS.name());
		values.put("status", STATUS.CURRENT.name());
		values.put("code", "WIN8APPS");
		values.put("instructor", "TBA");
		db.insert(TABLE_COURSE, null, values);
		
		// upcoming courses
		values =  new ContentValues();
		values.put("name", "Certificate in iPhone Application Development");
		values.put("type", APPTYPE.IOS.name());
		values.put("status", STATUS.UPCOMING.name());
		values.put("code", "IPHONEDEV");
		values.put("instructor", "Leslie Tsang");
		db.insert(TABLE_COURSE, null, values);
		
		values =  new ContentValues();
		values.put("name", "Certificate in iPad Application Development");
		values.put("type", APPTYPE.IOS.name());
		values.put("status", STATUS.UPCOMING.name());
		values.put("code", "IPADDEV");
		values.put("instructor", "Leslie Tsang");
		db.insert(TABLE_COURSE, null, values);
		
		values =  new ContentValues();
		values.put("name", "dummy course 1");
		values.put("type", APPTYPE.OTHER.name());
		values.put("status", STATUS.UPCOMING.name());
		values.put("code", "OTHER1");
		values.put("instructor", "xxx");
		db.insert(TABLE_COURSE, null, values);
		
		values =  new ContentValues();
		values.put("name", "dummy course 2");
		values.put("type", APPTYPE.OTHER.name());
		values.put("status", STATUS.UPCOMING.name());
		values.put("code", "OTHER2");
		values.put("instructor", "yyy yyy");
		db.insert(TABLE_COURSE, null, values);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion > oldVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
			onCreate(db);
		}
	}

}
