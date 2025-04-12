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

import com.tonilr.ClassManager.Model.Class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/classes")
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
    
    @GetMapping
    public ResponseEntity<Page<Class>> getClasses(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(classService.getAllClasses(pageable));
    }

    private String getUsernameFromContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return principal.toString();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ClassResponse> getClassById(@PathVariable Long id) {
        String username = getUsernameFromContext();
        return ResponseEntity.ok(classService.getClassById(id, username));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassResponse> updateClass(@PathVariable Long id, @RequestBody ClassRequest request) {
        String username = getUsernameFromContext();
        return ResponseEntity.ok(classService.updateClass(id, request, username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        String username = getUsernameFromContext();
        classService.deleteClass(id, username);
        return ResponseEntity.noContent().build();
    }
}
