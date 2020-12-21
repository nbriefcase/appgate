package com.neirodiaz.appgate.service.impl;

import com.neirodiaz.appgate.exception.BacklogNotFoundException;
import com.neirodiaz.appgate.exception.CalculatorException;
import com.neirodiaz.appgate.exception.SessionNotFoundException;
import com.neirodiaz.appgate.model.Backlog;
import com.neirodiaz.appgate.model.SessionData;
import com.neirodiaz.appgate.model.type.Action;
import com.neirodiaz.appgate.repository.BacklogRepository;
import com.neirodiaz.appgate.repository.SessionDataRepository;
import com.neirodiaz.appgate.service.CalculatorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
public class CalculatorServiceImpl implements CalculatorService {

    @Autowired
    private SessionDataRepository sessionDataRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public CalculatorServiceImpl() {
    }

    CalculatorServiceImpl(SessionDataRepository sessionDataRepository, BacklogRepository backlogRepository) {
        this.sessionDataRepository = sessionDataRepository;
        this.backlogRepository = backlogRepository;
    }

    @Override
    public UUID createSession() {
        UUID uuid = UUID.randomUUID();

        SessionData session = SessionData.builder()
                .id(uuid.toString())
                .created(LocalDateTime.now())
                .updated(null)
                .build();
        sessionDataRepository.saveAndFlush(session);
        return uuid;
    }

    @Override
    public List<SessionData> getSessionList() {
        return sessionDataRepository.findAll();
    }

    @Override
    public SessionData getSessionById(String sessionId) {
        return getSession(sessionId);
    }

    @Override
    public void deleteSession(String sessionId) {
        SessionData sessionData = getSession(sessionId);
        sessionDataRepository.delete(sessionData);
    }

    @Override
    public Backlog addOperand(String sessionId, Double operand) {
        SessionData sessionData = getSession(sessionId);
        Backlog backlog = Backlog.builder()
                .number(operand)
                .session(sessionData)
                .build();
        sessionData.setUpdated(LocalDateTime.now());
        backlogRepository.save(backlog);
        sessionDataRepository.save(sessionData);
        return backlog;
    }

    @Override
    public Double calculate(String sessionId, String action) {
        SessionData sessionData = getSession(sessionId);
        List<Backlog> backlogList = backlogRepository.findAllBySession(sessionData);

        if (backlogList.isEmpty()) {
            throw new BacklogNotFoundException("No se encontraron operandos para realizar el calculo para la sesion:" + sessionId);
        } else if (backlogList.size() == 1) {
            throw new CalculatorException("Debe ingresar por lo menos un operando más para la sesion:" + sessionId);
        }

        double resultado = 0.0;
        List<Double> numbers = backlogList.stream()
                .map(Backlog::getNumber)
                .collect(Collectors.toList());

        try {
            Action accion = Enum.valueOf(Action.class, action);
            switch (accion) {
                case suma:
                    resultado = backlogList.stream()
                            .map(Backlog::getNumber)
                            .reduce(0d, Double::sum);
                    break;
                case resta:
                    resultado = numbers.get(0);
                    for (int i = 1; i < numbers.size(); i++) {
                        resultado -= numbers.get(i);
                    }
                    break;
                case multiplicacion:
                    resultado = backlogList.stream()
                            .map(Backlog::getNumber)
                            .reduce(1d, (a, b) -> a * b);
                    break;
                case division:
                    resultado = numbers.get(0);
                    for (int i = 1; i < numbers.size(); i++) {
                        resultado /= numbers.get(i);
                    }
                    break;
                case potenciacion:
                    resultado = numbers.get(0);
                    for (int i = 1; i < numbers.size(); i++) {
                        resultado = Math.pow(resultado, numbers.get(i));
                    }
            }
        } catch (IllegalArgumentException e) {
            throw new CalculatorException("Acción desconocida: " + action);
        }
        return resultado;
    }

    private SessionData getSession(String sessionId) {
        return sessionDataRepository.findById(sessionId)
                .orElseThrow(() -> new SessionNotFoundException("El Id de la session no existe, sessionId:" + sessionId));
    }
}
