package com.tonilr.ClassManager.Controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tonilr.ClassManager.DTO.StudentRequest;
import com.tonilr.ClassManager.DTO.StudentResponse;
import com.tonilr.ClassManager.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.tonilr.ClassManager.Model.Student;
import com.tonilr.ClassManager.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/students")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    // ELIMINADO: @GetMapping("/all") - PELIGROSO para memoria
    // En su lugar, usar siempre paginación
    
    @GetMapping
    public ResponseEntity<Page<Student>> getStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size, // Reducido de 10 a 20 para mejor UX
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        // Validar parámetros para evitar valores extremos
        page = Math.max(0, Math.min(page, 100)); // Máximo 100 páginas
        size = Math.max(5, Math.min(size, 100)); // Entre 5 y 100 elementos
        
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        
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

    @PostMapping("/{studentId}/assign/{classId}")
    public ResponseEntity<Void> assignStudentToClass(@PathVariable Long studentId, @PathVariable Long classId) {
        String username = getUsernameFromContext();
        studentService.assignToClass(studentId, classId, username);
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
        if (principal instanceof User user) {
            return user.getUsername();
        }
        return principal.toString();
    }
}