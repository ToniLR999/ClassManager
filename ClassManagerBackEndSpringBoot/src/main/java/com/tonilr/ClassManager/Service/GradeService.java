package com.tonilr.ClassManager.Service;


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

	public Grade registerGrade(Long studentId, Long classId, Double value, String description, String username) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Class clazz = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        if (!clazz.getProfessor().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized to grade this class");
        }

        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setClazz(clazz);
        grade.setValue(value);
        grade.setDescription(description);
        grade.setDate(LocalDate.now());
        return gradeRepository.save(grade);
    }

    public List<Grade> getGradesByStudent(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    public List<Grade> getGradesByClass(Long classId) {
        return gradeRepository.findByClazzId(classId);
    }
}
