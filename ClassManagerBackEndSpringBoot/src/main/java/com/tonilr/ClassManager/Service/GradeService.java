package com.tonilr.ClassManager.Service;


import com.tonilr.ClassManager.DTO.StudentRequest;
import com.tonilr.ClassManager.DTO.StudentResponse;
import com.tonilr.ClassManager.Model.Class;
import com.tonilr.ClassManager.Model.Grade;
import com.tonilr.ClassManager.Model.Student;
import com.tonilr.ClassManager.Repository.ClassRepository;
import com.tonilr.ClassManager.Repository.GradeRepository;
import com.tonilr.ClassManager.Repository.StudentRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {

	@Autowired
    private final GradeRepository gradeRepository;
	
	@Autowired
	private final StudentRepository studentRepository;
    
	@Autowired
	private final ClassRepository classRepository;
	
	

    public GradeService(GradeRepository gradeRepository, StudentRepository studentRepository,
			ClassRepository classRepository) {
		super();
		this.gradeRepository = gradeRepository;
		this.studentRepository = studentRepository;
		this.classRepository = classRepository;
	}

	public Grade registerGrade(Long studentId, Long classId, String subject, Double value, String description, String username) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Class clazz = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        if (!clazz.getProfessor().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized to grade this class");
        }

        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setSubject(subject);
        grade.setClazz(clazz);
        grade.setValue(value);
        grade.setDescription(description);
        grade.setDate(LocalDate.now());
        return gradeRepository.save(grade);
    }
	
    public Grade updateGrade(Long id, Double score, String subject) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found"));
        grade.setDescription(subject);
        grade.setValue(score);
        return gradeRepository.save(grade);
    }

    public void deleteGrade(Long id) {
    	gradeRepository.deleteById(id);
    }

    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }
	
    public List<Grade> getGradesByStudent(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    public List<Grade> getGradesByClass(Long classId) {
        return gradeRepository.findByClazzId(classId);
    }
}
