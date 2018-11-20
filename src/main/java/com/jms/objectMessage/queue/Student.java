package com.jms.objectMessage.queue;

import java.io.Serializable;

public class Student implements Serializable {
	public String name;
	public String studentId;
	public String faculty;
	public String college;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", studentId=" + studentId + ", faculty=" + faculty + ", college=" + college
				+ "]";
	}

}
