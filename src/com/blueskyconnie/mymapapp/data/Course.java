package com.blueskyconnie.mymapapp.data;

public class Course {
	
	public enum STATUS  { TAKEN, CURRENT, UPCOMING };
	
	private String courseType;
	private String courseName;
	private STATUS courseStatus;
	
	public Course() {
		
	}
	
	public String getCourseType() {
		return courseType;
	}
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public STATUS getCourseStatus() {
		return courseStatus;
	}
	public void setCourseStatus(STATUS courseStatus) {
		this.courseStatus = courseStatus;
	}

}
