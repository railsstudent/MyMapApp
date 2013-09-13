package com.blueskyconnie.mymapapp.data;

public class Course {
	
	public enum STATUS  { TAKEN, CURRENT, UPCOMING };
	public enum APPTYPE  { ANDROID, IOS };
	
	private APPTYPE courseType;
	private String courseName;
	private STATUS courseStatus;
	private String code;
	private String instructor;
	
	// "http://www1.fevaworks.com/portal/site/course.asp?code=ANDROIDAIO&categoryid=14"
	public Course() {
		
	}
	
	public Course(String name, APPTYPE apptype, STATUS status, String code, String instructor) {
		this.courseName = name;
		this.courseType = apptype;
		this.courseStatus = status;
		this.code = code;
		this.instructor = instructor;
	}
	
	public APPTYPE getCourseType() {
		return courseType;
	}
	public void setCourseType(APPTYPE courseType) {
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((courseName == null) ? 0 : courseName.hashCode());
		result = prime * result
				+ ((courseStatus == null) ? 0 : courseStatus.hashCode());
		result = prime * result
				+ ((courseType == null) ? 0 : courseType.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (courseName == null) {
			if (other.courseName != null)
				return false;
		} else if (!courseName.equals(other.courseName))
			return false;
		if (courseStatus != other.courseStatus)
			return false;
		if (courseType != other.courseType)
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
}
