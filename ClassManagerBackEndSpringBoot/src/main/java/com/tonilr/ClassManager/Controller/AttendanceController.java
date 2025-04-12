package com.tonilr.ClassManager.Controller;

import com.tonilr.ClassManager.Model.Attendance;
import com.tonilr.ClassManager.Service.AttendanceService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

	@Autowired
    private final AttendanceService attendanceService;
	

    public AttendanceController(AttendanceService attendanceService) {
		super();
		this.attendanceService = attendanceService;
	}

	@PostMapping("/mark")
    public ResponseEntity<Void> markAttendance(@RequestParam Long studentId, @RequestParam Long classId) {
        String username = getUsername();
        attendanceService.markAttendance(studentId, classId, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Attendance>> getAttendanceByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByStudent(studentId));
    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<List<Attendance>> getAttendanceByClass(@PathVariable Long classId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByClass(classId));
    }

    private String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return principal.toString();
    }
}
