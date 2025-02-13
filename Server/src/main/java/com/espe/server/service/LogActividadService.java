package com.espe.server.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espe.server.persistence.entity.LogActividad;
import com.espe.server.persistence.repository.ILogActividadRepository;

@Service
public class LogActividadService {

    @Autowired
    private ILogActividadRepository logActividadRepository;

    // Obtener todos los logs de actividad
    public List<LogActividad> findAllLogs() {
        return (List<LogActividad>)logActividadRepository.findAll();
    }

    // Obtener un log de actividad por su ID
    public Optional<LogActividad> findLogById(Long idLog) {
        return logActividadRepository.findById(idLog);
    }

    // Crear un nuevo log de actividad
    public LogActividad createLog(LogActividad newLog) {
        return logActividadRepository.save(newLog);
    }

    // Actualizar un log de actividad existente
    public Optional<LogActividad> updateLog(Long idLog, LogActividad updatedLog) {
        return logActividadRepository.findById(idLog).map(log -> {
            log.setAccion(updatedLog.getAccion());
            log.setFechaAccion(updatedLog.getFechaAccion());
            log.setDetalles(updatedLog.getDetalles());
            return logActividadRepository.save(log);
        });
    }

    // Eliminar un log de actividad por su ID
    public boolean deleteLog(Long idLog) {
        if (logActividadRepository.existsById(idLog)) {
            logActividadRepository.deleteById(idLog);
            return true;
        } else {
            return false;
        }
    }

    // Obtener todos los logs de actividad asociados a un usuario
    public List<LogActividad> findLogsByUsuarioId(Long idUsuario) {
        return logActividadRepository.findByUsuarioId(idUsuario);
    }
}