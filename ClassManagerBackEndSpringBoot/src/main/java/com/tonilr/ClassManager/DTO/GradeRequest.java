package com.tonilr.ClassManager.DTO;

import lombok.Data;

@Data
public class GradeRequest {
    private Long studentId;
    private Long classId;
    private String subject;
    private Double value;
    private String description;
    
	public GradeRequest(Long studentId, Long classId, String subject, Double value, String description) {
		super();
		this.studentId = studentId;
		this.classId = classId;
		this.subject = subject;
		this.value = value;
		this.description = description;
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
    
    
}