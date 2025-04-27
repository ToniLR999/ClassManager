package com.tonilr.ClassManager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;


public class GradeResponse {
    private Long id;
    private String subject;
    private Double value;
    private String description;
    private String studentName; // --> nombre del alumno
    private String className;   // --> nombre de la clase
    
    
	public GradeResponse(Long id, String subject, Double value, String description, String studentName,
			String className) {
		super();
		this.id = id;
		this.subject = subject;
		this.value = value;
		this.description = description;
		this.studentName = studentName;
		this.className = className;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
    
    
    
}