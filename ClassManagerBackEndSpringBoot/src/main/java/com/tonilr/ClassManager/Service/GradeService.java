package com.tonilr.ClassManager.Service;


import com.tonilr.ClassManager.DTO.GradeRequest;
import com.tonilr.ClassManager.DTO.GradeResponse;
import com.tonilr.ClassManager.Model.Class;
import com.tonilr.ClassManager.Model.Grade;
import com.tonilr.ClassManager.Model.Student;
import com.tonilr.ClassManager.Repository.ClassRepository;
import com.tonilr.ClassManager.Repository.GradeRepository;
import com.tonilr.ClassManager.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
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

	public GradeResponse registerGrade(GradeRequest request, String username) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Class clazz = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        if (!clazz.getProfessor().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized to grade this class");
        }

        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setSubject(request.getSubject());
        grade.setClazz(clazz);
        grade.setValue(request.getValue());
        grade.setDescription(request.getDescription());
        grade.setDate(LocalDate.now());
        Grade updated = gradeRepository.save(grade);
        return new GradeResponse(updated.getId(),updated.getSubject(),updated.getValue(),updated.getDescription(),updated.getStudent().getFullName(),updated.getClass().getName());
        }
	
    public GradeResponse updateGrade(Long id, Double score, String subject) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found"));
        grade.setDescription(subject);
        grade.setValue(score);
        Grade updated = gradeRepository.save(grade);
        return new GradeResponse(updated.getId(),updated.getSubject(),updated.getValue(),updated.getDescription(),updated.getStudent().getFullName(),updated.getClass().getName());
    }

    public void deleteGrade(Long id) {
    	gradeRepository.deleteById(id);
    }

    public List<GradeResponse> getAllGrades() {
        List<Grade> grades = gradeRepository.findAll();
        return grades.stream()
                .map(this::convertToGradeResponse)
                .collect(Collectors.toList());
    }
    
    private GradeResponse convertToGradeResponse(Grade grade) {
        return new GradeResponse(
                grade.getId(),
                grade.getSubject(),
                grade.getValue(),
                grade.getDescription(),
                grade.getStudent().getFullName(),
                grade.getClass().getName()
        );
    }
	
    public List<Grade> getGradesByStudent(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    public List<Grade> getGradesByClass(Long classId) {
        return gradeRepository.findByClazzId(classId);
    }
}
