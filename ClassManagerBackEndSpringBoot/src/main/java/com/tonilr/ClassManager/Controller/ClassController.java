package com.tonilr.ClassManager.Controller;

import com.tonilr.ClassManager.DTO.ClassRequest;
import com.tonilr.ClassManager.DTO.ClassResponse;
import com.tonilr.ClassManager.Service.ClassService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClassController {

	@Autowired
    private final ClassService classService;
	
    public ClassController(ClassService classService) {
		this.classService = classService;
	}

	@PostMapping
    public ResponseEntity<ClassResponse> createClass(@RequestBody ClassRequest request) {
        String username = getUsernameFromContext();
        return ResponseEntity.ok(classService.createClass(request, username));
    }

    @GetMapping
    public ResponseEntity<List<ClassResponse>> getMyClasses() {
        String username = getUsernameFromContext();
        return ResponseEntity.ok(classService.getClassesByProfessor(username));
    }

    private String getUsernameFromContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return principal.toString();
    }
}
