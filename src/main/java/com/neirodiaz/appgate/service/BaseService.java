package com.neirodiaz.appgate.service;

import com.neirodiaz.appgate.model.Audit;

import java.time.LocalDateTime;
import java.util.List;

public interface BaseService {

    void auditar(String sessionId, String accion, String value);

    List<Audit> getAuditDataByDate(LocalDateTime data);
}
