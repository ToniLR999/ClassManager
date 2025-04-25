package com.tonilr.ClassManager.Controller;

import com.tonilr.ClassManager.Model.Grade;
import com.tonilr.ClassManager.Service.GradeService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
@RequiredArgsConstructor
public class GradeController {

	@Autowired
    private final GradeService gradeService;

	
	
    public GradeController(GradeService gradeService) {
		super();
		this.gradeService = gradeService;
	}

	@PostMapping("/register")
    public ResponseEntity<Grade> registerGrade(@RequestParam Long studentId,
                                               @RequestParam Long classId,
                                               @RequestParam Double value,
                                               @RequestParam String subject,
                                               @RequestParam(required = false) String description) {
        String username = getUsername();
        Grade grade = gradeService.registerGrade(studentId, classId, subject, value, description, username);
        return ResponseEntity.ok(grade);
    }
	
    @PutMapping("/{id}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @RequestParam Double score,@RequestParam String subject) {
        return ResponseEntity.ok(gradeService.updateGrade(id, score, subject));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
    	gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Grade>> getAllGrades() {
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
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return principal.toString();
    }
    
}