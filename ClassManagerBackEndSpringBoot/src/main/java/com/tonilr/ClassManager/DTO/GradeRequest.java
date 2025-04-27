package com.tonilr.ClassManager.DTO;

import lombok.Data;

@Data
public class GradeRequest {
    private Long studentId;
    private Long classId;
    private String subject;
    private Double score;
    private String description;
    
	public GradeRequest(Long studentId, Long classId, String subject, Double score, String description) {
		super();
		this.studentId = studentId;
		this.classId = classId;
		this.subject = subject;
		this.score = score;
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

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
    
}