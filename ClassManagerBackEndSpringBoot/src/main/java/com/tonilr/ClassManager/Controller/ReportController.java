package com.tonilr.ClassManager.Controller;

import com.tonilr.ClassManager.Service.ReportService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

	@Autowired
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
		super();
		this.reportService = reportService;
	}

	@GetMapping("/grades/{classId}")
    public ResponseEntity<byte[]> generateGradesReport(@PathVariable Long classId) throws Exception {
        String username = getUsername();
        byte[] pdfBytes = reportService.generateGradesReport(classId, username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "calificaciones-clase-" + classId + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    private String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return principal.toString();
    }
}
