package com.tonilr.ClassManager.DTO;

import java.util.List;

public class ClassResponse {
    private Long id;
    private String name;
    private String description;
    private List<String> subjects;
    private String schedule;
    private String professorUsername;
    

    
	public ClassResponse(Long id, String name, String description, List<String> subjects, String schedule,
			String professorUsername) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.subjects = subjects;
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
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getProfessorUsername() {
		return professorUsername;
	}
	public void setProfessorUsername(String professorUsername) {
		this.professorUsername = professorUsername;
	}

	public List<String> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}

	


}