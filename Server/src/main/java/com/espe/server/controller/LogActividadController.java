package com.espe.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.espe.server.persistence.entity.LogActividad;
import com.espe.server.service.LogActividadService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/logs-actividades")
public class LogActividadController {

    @Autowired
    private LogActividadService logActividadService;

    // Obtener todos los logs de actividad
    @GetMapping
    public ResponseEntity<?> findAllLogs() {
        try {
            List<LogActividad> logs = logActividadService.findAllLogs();
            return ResponseEntity.status(HttpStatus.OK).body(logs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los logs de actividad: " + e.getMessage());
        }
    }

    // Obtener un log de actividad por su ID
    @GetMapping("/{idLog}")
    public ResponseEntity<?> findLogById(@PathVariable("idLog") Long idLog) {
        try {
            Optional<LogActividad> log = logActividadService.findLogById(idLog);
            if (log.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(log.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Log de actividad no encontrado con ID: " + idLog);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener el log de actividad: " + e.getMessage());
        }
    }

    // Crear un nuevo log de actividad
    @PostMapping
    public ResponseEntity<?> createLog(@RequestBody LogActividad newLog) {
        try {
            LogActividad logCreado = logActividadService.createLog(newLog);
            return ResponseEntity.status(HttpStatus.CREATED).body(logCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el log de actividad: " + e.getMessage());
        }
    }

    // Actualizar un log de actividad existente
    @PutMapping("/{idLog}")
    public ResponseEntity<?> updateLog(@PathVariable("idLog") Long idLog, @RequestBody LogActividad updatedLog) {
        try {
            Optional<LogActividad> logActualizado = logActividadService.updateLog(idLog, updatedLog);
            if (logActualizado.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(logActualizado.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Log de actividad no encontrado con ID: " + idLog);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el log de actividad: " + e.getMessage());
        }
    }

    // Eliminar un log de actividad por su ID
    @DeleteMapping("/{idLog}")
    public ResponseEntity<?> deleteLog(@PathVariable("idLog") Long idLog) {
        try {
            boolean eliminado = logActividadService.deleteLog(idLog);
            if (eliminado) {
                return ResponseEntity.status(HttpStatus.OK).body("Log de actividad eliminado correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Log de actividad no encontrado con ID: " + idLog);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el log de actividad: " + e.getMessage());
        }
    }

    // Obtener todos los logs de actividad asociados a un usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> findLogsByUsuarioId(@PathVariable("idUsuario") Long idUsuario) {
        try {
            List<LogActividad> logs = logActividadService.findLogsByUsuarioId(idUsuario);
            return ResponseEntity.status(HttpStatus.OK).body(logs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los logs de actividad: " + e.getMessage());
        }
    }
}