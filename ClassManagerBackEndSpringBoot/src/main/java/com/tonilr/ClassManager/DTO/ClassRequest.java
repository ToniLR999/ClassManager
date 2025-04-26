package com.tonilr.ClassManager.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ClassRequest {
    private String name;
    private String description;
    private List<String> subjects;
    private String schedule;
    
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
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public List<String> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}
	
	
    
    
}