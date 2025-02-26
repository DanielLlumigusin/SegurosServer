package com.espe.server.controller.admin;

import com.espe.server.persistence.entity.LogActividad;
import com.espe.server.service.LogActividadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/logs-actividades")
public class AdminLogActividadController {

    private final LogActividadService logActividadService;
    
    public AdminLogActividadController(LogActividadService logActividadService) {
    	this.logActividadService = logActividadService;
    }
    

    @GetMapping
    public ResponseEntity<List<LogActividad>> findAllLogs() {
        try {
            List<LogActividad> logs = logActividadService.findAllLogs();
            return ResponseEntity.status(HttpStatus.OK).body(logs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/admin/{idLog}")
    public ResponseEntity<LogActividad> findLogById(@PathVariable("idLog") Long idLog) {
        try {
            Optional<LogActividad> log = logActividadService.findLogById(idLog);
            if (log.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(log.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<LogActividad> createLog(@RequestBody LogActividad newLog) {
        try {
            LogActividad logCreado = logActividadService.createLog(newLog);
            return ResponseEntity.status(HttpStatus.CREATED).body(logCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{idLog}")
    public ResponseEntity<LogActividad> updateLog(@PathVariable("idLog") Long idLog, @RequestBody LogActividad updatedLog) {
        try {
            Optional<LogActividad> logActualizado = logActividadService.updateLog(idLog, updatedLog);
            if (logActualizado.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(logActualizado.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idLog}")
    public ResponseEntity<Void> deleteLog(@PathVariable("idLog") Long idLog) {
        try {
            boolean eliminado = logActividadService.deleteLog(idLog);
            if (eliminado) {
                return ResponseEntity.noContent().build(); 
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    
}