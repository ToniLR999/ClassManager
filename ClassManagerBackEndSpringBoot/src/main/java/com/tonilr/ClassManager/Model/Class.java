package com.tonilr.ClassManager.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classes")
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDateTime schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private User professor;
    
    @ManyToMany
    @JoinTable(
        name = "class_students",
        joinColumns = @JoinColumn(name = "class_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students = new HashSet<>();


	public Class() {
	}

	public Class(Long id, String name, String description, LocalDateTime schedule, User professor) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.schedule = schedule;
		this.professor = professor;
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

	public User getProfessor() {
		return professor;
	}

	public void setProfessor(User professor) {
		this.professor = professor;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	
	
    
    
}