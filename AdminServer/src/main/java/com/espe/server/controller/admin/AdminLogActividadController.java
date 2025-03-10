package com.espe.server.controller.admin;

import com.espe.server.persistence.entity.LogActividad;
import com.espe.server.service.LogActividadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/logs")
public class AdminLogActividadController {

    private final LogActividadService logActividadService; 
    
    public AdminLogActividadController(LogActividadService logActividadService) {
    	this.logActividadService = logActividadService;
    }
    
    
    @GetMapping
    public ResponseEntity<?> findAllLogs() {
        try {
            List<LogActividad> logs = logActividadService.findAllLogs();
            return ResponseEntity.ok(logs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    
}