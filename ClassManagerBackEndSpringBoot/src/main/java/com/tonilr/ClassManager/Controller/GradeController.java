package com.tonilr.ClassManager.Controller;

import com.tonilr.ClassManager.DTO.GradeRequest;
import com.tonilr.ClassManager.DTO.GradeResponse;
import com.tonilr.ClassManager.Model.Grade;
import com.tonilr.ClassManager.Model.User;
import com.tonilr.ClassManager.Service.GradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
public class GradeController {

	@Autowired
    private final GradeService gradeService;

	
	
    public GradeController(GradeService gradeService) {
		super();
		this.gradeService = gradeService;
	}

	@PostMapping("/register")
    public ResponseEntity<GradeResponse> registerGrade(@RequestBody GradeRequest gradeRequest) {
        String username = getUsername();
        return ResponseEntity.ok(gradeService.registerGrade(gradeRequest, username));
    }
	
    @PutMapping("/{id}")
    public ResponseEntity<GradeResponse> updateGrade(@PathVariable Long id, @RequestParam Double score,@RequestParam String subject) {
        return ResponseEntity.ok(gradeService.updateGrade(id, score, subject));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
    gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<GradeResponse>> getAllGrades() {
        return ResponseEntity.ok(gradeService.getAllGrades());
    }
	
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Grade>> getGradesByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(gradeService.getGradesByStudent(studentId));
    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<List<Grade>> getGradesByClass(@PathVariable Long classId) {
        return ResponseEntity.ok(gradeService.getGradesByClass(classId));
    }

    private String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User user) {
            return user.getUsername();
        }
        return principal.toString();
    }
    
}