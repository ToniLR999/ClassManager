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
                                               @RequestParam(required = false) String description) {
        String username = getUsername();
        Grade grade = gradeService.registerGrade(studentId, classId, value, description, username);
        return ResponseEntity.ok(grade);
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