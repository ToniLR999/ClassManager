package com.tonilr.ClassManager.Service;

import com.tonilr.ClassManager.DTO.StudentRequest;
import com.tonilr.ClassManager.DTO.StudentResponse;
import com.tonilr.ClassManager.Model.Class;
import com.tonilr.ClassManager.Model.Student;
import com.tonilr.ClassManager.Repository.ClassRepository;
import com.tonilr.ClassManager.Repository.StudentRepository;
import com.tonilr.ClassManager.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
	
	@Autowired
    private final StudentRepository studentRepository;
    
	@Autowired
	private final ClassRepository classRepository;
    
	public StudentService(StudentRepository studentRepository, ClassRepository classRepository,
			UserRepository userRepository) {
		super();
		this.studentRepository = studentRepository;
		this.classRepository = classRepository;
	}

	public StudentResponse create(StudentRequest request) {
        Student student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        Student saved = studentRepository.save(student);
        return toResponse(saved);
    }

    public List<StudentResponse> getAll() {
        return studentRepository.findAll()
                .stream().map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public StudentResponse getById(Long id) {
        return studentRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public StudentResponse update(Long id, StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        return toResponse(studentRepository.save(student));
    }

    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

    public void assignToClass(Long studentId, Long classId, String username) {
        Class clazz = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        if (!clazz.getProfessor().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized to assign student to this class");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        clazz.getStudents().add(student);
        student.getClasses().add(clazz);

        classRepository.save(clazz);
        studentRepository.save(student);
    }
    
    public void removeFromClass(Long studentId, Long classId, String username) {
        Class clazz = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        if (!clazz.getProfessor().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized to remove student from this class");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        clazz.getStudents().remove(student);
        student.getClasses().remove(clazz);

        classRepository.save(clazz);
        studentRepository.save(student);
    }

    public Set<StudentResponse> getStudentsByClass(Long classId, String username) {
        Class clazz = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        if (!clazz.getProfessor().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized access to class students");
        }

        return clazz.getStudents()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toSet());
    }

    private StudentResponse toResponse(Student s) {
        return new StudentResponse(s.getId(), s.getFirstName(), s.getLastName(), s.getEmail());
    }
}