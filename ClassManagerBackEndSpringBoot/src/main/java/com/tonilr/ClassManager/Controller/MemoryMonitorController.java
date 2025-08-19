package com.tonilr.ClassManager.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/memory")
@PreAuthorize("hasRole('ADMIN')")
public class MemoryMonitorController {

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getMemoryStatus() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryBean.getNonHeapMemoryUsage();
        
        Runtime runtime = Runtime.getRuntime();
        
        Map<String, Object> memoryInfo = new HashMap<>();
        
        // Información del heap
        memoryInfo.put("heapUsed", formatBytes(heapMemoryUsage.getUsed()));
        memoryInfo.put("heapMax", formatBytes(heapMemoryUsage.getMax()));
        memoryInfo.put("heapCommitted", formatBytes(heapMemoryUsage.getCommitted()));
        memoryInfo.put("heapUsagePercentage", 
            String.format("%.2f%%", (double) heapMemoryUsage.getUsed() / heapMemoryUsage.getMax() * 100));
        
        // Información de memoria no-heap
        memoryInfo.put("nonHeapUsed", formatBytes(nonHeapMemoryUsage.getUsed()));
        memoryInfo.put("nonHeapCommitted", formatBytes(nonHeapMemoryUsage.getCommitted()));
        
        // Información del sistema
        memoryInfo.put("totalMemory", formatBytes(runtime.totalMemory()));
        memoryInfo.put("freeMemory", formatBytes(runtime.freeMemory()));
        memoryInfo.put("maxMemory", formatBytes(runtime.maxMemory()));
        
        // Alertas de memoria
        double heapUsagePercent = (double) heapMemoryUsage.getUsed() / heapMemoryUsage.getMax() * 100;
        if (heapUsagePercent > 90) {
            memoryInfo.put("alert", "CRÍTICO: Uso de heap > 90%");
        } else if (heapUsagePercent > 80) {
            memoryInfo.put("alert", "ADVERTENCIA: Uso de heap > 80%");
        } else if (heapUsagePercent > 70) {
            memoryInfo.put("alert", "ATENCIÓN: Uso de heap > 70%");
        } else {
            memoryInfo.put("alert", "NORMAL: Uso de heap OK");
        }
        
        return ResponseEntity.ok(memoryInfo);
    }
    
    private String formatBytes(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp-1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }
}
