package com.tonilr.ClassManager.DTO;

import java.time.LocalDate;

public class AttendanceResponse {
    private Long id;
    private LocalDate date;
    private Long studentId;
    private String studentFirstName;
    private String studentLastName;
    private Long classId;
    private String className;
    
	public AttendanceResponse(Long id, LocalDate date, Long studentId, String studentFirstName, String studentLastName,
			Long classId, String className) {
		super();
		this.id = id;
		this.date = date;
		this.studentId = studentId;
		this.studentFirstName = studentFirstName;
		this.studentLastName = studentLastName;
		this.classId = classId;
		this.className = className;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public String getStudentFirstName() {
		return studentFirstName;
	}
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}
	public String getStudentLastName() {
		return studentLastName;
	}
	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
}