package com.neirodiaz.appgate.controller;

import com.neirodiaz.appgate.exception.BacklogNotFoundException;
import com.neirodiaz.appgate.exception.CalculatorException;
import com.neirodiaz.appgate.exception.SessionNotFoundException;
import com.neirodiaz.appgate.model.SessionData;
import com.neirodiaz.appgate.service.CalculatorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
public class CalculatorController {

    @Autowired
    private CalculatorService service;

    @PostMapping("/addOperand")
    public void addOperand(@RequestParam String sessionId,
                           @RequestParam Long num) {
        try {
            service.addOperand(sessionId, (double) num);
        } catch (SessionNotFoundException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e.getCause());
        }
    }

    @GetMapping("/calculate")
    public Double calculate(@RequestParam String sessionId,
                            @RequestParam String action) {
        Double calculate;
        try {
            calculate = service.calculate(sessionId, action);
        } catch (SessionNotFoundException | CalculatorException | BacklogNotFoundException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e.getCause());
        }
        return calculate;
    }

    @GetMapping("/Sessions")
    public List<SessionData> sessionList(@RequestParam(required = false) String sessionId) {
        List<SessionData> result;
        try {
            Optional<String> session = Optional.ofNullable(sessionId);
            if (!session.isPresent()) {
                result = service.getSessionList();
            } else {
                result = Collections.singletonList(service.getSessionById(sessionId));
            }
        } catch (SessionNotFoundException | CalculatorException | BacklogNotFoundException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e.getCause());
        }
        return result;
    }

    @DeleteMapping("/Sessions")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteSession(@RequestParam String sessionId) {
        try {
            service.deleteSession(sessionId);
        } catch (SessionNotFoundException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e.getCause());
        }
    }

    @GetMapping("/createSession")
    public UUID createSession() {
        return service.createSession();
    }
}
