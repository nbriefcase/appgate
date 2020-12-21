package com.neirodiaz.appgate.service;

import com.neirodiaz.appgate.model.Audit;
import com.neirodiaz.appgate.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public abstract class BaseServiceImpl implements BaseService {

    @Autowired
    private AuditRepository auditRepository;

    @Override
    public void auditar(String sessionId, String accion, String value) {

        Audit auditar = Audit.builder()
                .sessionId(sessionId)
                .action(accion)
                .value(value)
                .date(LocalDateTime.now())
                .build();

        auditRepository.save(auditar);
    }

    @Override
    public List<Audit> getAuditDataByDate(LocalDateTime data) {
        return auditRepository.findAllInDate(data);
    }

}
