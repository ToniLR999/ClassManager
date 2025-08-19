package com.tonilr.ClassManager.Controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;

@RestController
@RequestMapping("/api/system")
public class SystemStatusController {

    private final Environment environment;

    public SystemStatusController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        boolean isProd = isProduction();
        ZoneId zone = ZoneId.of("Europe/Madrid");
        ZonedDateTime now = ZonedDateTime.now(zone);
        
        // En desarrollo siempre está activo
        boolean active = !isProd || isWithinBusinessHours(now);
        
        Map<String, Object> body = new HashMap<>();
        body.put("active", active);
        body.put("maintenance", !active);
        body.put("now", now.toString());
        body.put("timezone", zone.getId());
        body.put("businessDays", "MON-FRI");
        body.put("fromHour", 10);
        body.put("toHour", 19);
        body.put("isProduction", isProd);
        body.put("currentTime", now.toLocalTime().toString());
        body.put("currentDay", now.getDayOfWeek().toString());
        
        return body;
    }

    private boolean isProduction() {
        Set<String> prods = Set.of("prod", "production", "railway");
        return Arrays.stream(environment.getActiveProfiles())
                .anyMatch(prods::contains);
    }

    private boolean isWithinBusinessHours(ZonedDateTime now) {
        DayOfWeek dow = now.getDayOfWeek();
        if (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY) {
            return false;
        }
        LocalTime time = now.toLocalTime();
        LocalTime start = LocalTime.of(10, 0);
        LocalTime end = LocalTime.of(19, 0);
        return !time.isBefore(start) && !time.isAfter(end);
    }
}


