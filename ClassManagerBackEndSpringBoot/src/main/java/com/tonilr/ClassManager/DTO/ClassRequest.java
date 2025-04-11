package com.tonilr.ClassManager.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClassRequest {
    private String name;
    private String description;
    private LocalDateTime schedule;
    
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
    
    
}