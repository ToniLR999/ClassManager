package com.tonilr.ClassManager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ClassResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime schedule;
    private String professorUsername;
    
    public ClassResponse(Long id, String name, String description, LocalDateTime schedule, String professorUsername) {
    	this.id = id;
    	this.name = name;
    	this.description = description;
    	this.schedule = schedule;
    	this.professorUsername = professorUsername;
    	
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getSchedule() {
		return schedule;
	}
	public void setSchedule(LocalDateTime schedule) {
		this.schedule = schedule;
	}
	public String getProfessorUsername() {
		return professorUsername;
	}
	public void setProfessorUsername(String professorUsername) {
		this.professorUsername = professorUsername;
	}



}