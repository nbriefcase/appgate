package com.neirodiaz.appgate.service;

import com.neirodiaz.appgate.model.Audit;
import com.neirodiaz.appgate.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public abstract class BaseService {

    @Autowired
    private AuditRepository repository;

    public void auditar(String sessionId, String accion, String value) {

        Audit auditar = Audit.builder()
                .sessionId(sessionId)
                .action(accion)
                .value(value)
                .date(LocalDateTime.now())
                .build();

        repository.save(auditar);
    }

}
