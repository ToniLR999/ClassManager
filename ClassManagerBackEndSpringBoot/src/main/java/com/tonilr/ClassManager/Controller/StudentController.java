package com.tonilr.ClassManager.Controller;

import com.tonilr.ClassManager.DTO.StudentRequest;
import com.tonilr.ClassManager.DTO.StudentResponse;
import com.tonilr.ClassManager.Service.StudentService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.tonilr.ClassManager.Model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

	@Autowired
    private final StudentService studentService;
	
    public StudentController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}

	@PostMapping
    public ResponseEntity<StudentResponse> create(@RequestBody StudentRequest request) {
        return ResponseEntity.ok(studentService.create(request));
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentResponse>> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }
    
    @GetMapping
    public ResponseEntity<Page<Student>> getStudents(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(studentService.getAllStudents(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> update(@PathVariable Long id, @RequestBody StudentRequest request) {
        return ResponseEntity.ok(studentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/assign/{classId}")
    public ResponseEntity<Void> assignStudentToClass(@PathVariable Long id, @PathVariable Long classId) {
        String username = getUsernameFromContext();
        studentService.assignToClass(id, classId, username);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{id}/delete/{classId}")
    public ResponseEntity<Void> removeStudentFromClass(@PathVariable Long id, @PathVariable Long classId) {
        String username = getUsernameFromContext();
        studentService.removeFromClass(id, classId, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-class/{classId}")
    public ResponseEntity<Set<StudentResponse>> getStudentsByClass(@PathVariable Long classId) {
        String username = getUsernameFromContext();
        return ResponseEntity.ok(studentService.getStudentsByClass(classId, username));
    }

    private String getUsernameFromContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return principal.toString();
    }
}