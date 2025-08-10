package com.tonilr.ClassManager.Controller;

import com.tonilr.ClassManager.DTO.AttendanceResponse;
import com.tonilr.ClassManager.Model.Attendance;
import com.tonilr.ClassManager.Model.User;
import com.tonilr.ClassManager.Service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;   
import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

	@Autowired
    private final AttendanceService attendanceService;
	

    public AttendanceController(AttendanceService attendanceService) {
		super();
		this.attendanceService = attendanceService;
	}

    //Frontend envia la fecha donde se registra la attendance por si se quiere registrar de otro dia
	@PostMapping("/mark")
    public ResponseEntity<Void> markAttendance(@RequestParam Long studentId, @RequestParam Long classId) {
		System.out.println("studentId = " + studentId + ", classId = " + classId);

        String username = getUsername();
        attendanceService.markAttendance(studentId, classId, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceByStudent(@PathVariable Long studentId) {
        List<Attendance> attendanceList = attendanceService.getAttendanceByStudent(studentId);
        List<AttendanceResponse> response = attendanceList.stream()
                .map(a -> new AttendanceResponse(
                        a.getId(),
                        a.getDate(),
                        a.getStudent().getId(),
                        a.getStudent().getFirstName(),
                        a.getStudent().getLastName(),
                        a.getClazz().getId(),
                        a.getClazz().getName()))
                    .toList();
        return ResponseEntity.ok(response);    
   }

    @GetMapping("/class/{classId}")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceByClass(@PathVariable Long classId) {
        List<Attendance> attendanceList = attendanceService.getAttendanceByClass(classId);
        List<AttendanceResponse> response = attendanceList.stream()
            .map(a -> new AttendanceResponse(
                a.getId(),
                a.getDate(),
                a.getStudent().getId(),
                a.getStudent().getFirstName(),
                a.getStudent().getLastName(),
                a.getClazz().getId(),
                a.getClazz().getName()))
            .toList();
        return ResponseEntity.ok(response);      
    }

    private String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User user) {
            return user.getUsername();
        }
        return principal.toString();
    }
}
