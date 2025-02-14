package com.espe.server.service;

import com.espe.server.persistence.entity.LogActividad;
import com.espe.server.persistence.repository.ILogActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogActividadService {

    @Autowired
    private ILogActividadRepository logActividadRepository;

    public List<LogActividad> findAllLogs() {
        return (List<LogActividad>)logActividadRepository.findAll();
    }

    public Optional<LogActividad> findLogById(Long idLog) {
        return logActividadRepository.findById(idLog);
    }

    public LogActividad createLog(LogActividad newLog) {
        return logActividadRepository.save(newLog);
    }

    public Optional<LogActividad> updateLog(Long idLog, LogActividad updatedLog) {
        return logActividadRepository.findById(idLog).map(log -> {
            log.setAccion(updatedLog.getAccion());
            log.setFechaAccion(updatedLog.getFechaAccion());
            log.setDetalles(updatedLog.getDetalles());
            return logActividadRepository.save(log);
        });
    }

    public boolean deleteLog(Long idLog) {
        if (logActividadRepository.existsById(idLog)) {
            logActividadRepository.deleteById(idLog);
            return true;
        } else {
            return false;
        }
    }
}