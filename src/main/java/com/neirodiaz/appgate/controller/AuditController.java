package com.neirodiaz.appgate.controller;

import com.neirodiaz.appgate.model.Audit;
import com.neirodiaz.appgate.repository.AuditRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Log4j2
@RestController
public class AuditController {

    @Autowired
    private AuditRepository service;

    @GetMapping("/audit/{date}")
    @ApiOperation(value = "Muestra una lista de acciones realizadas para el dia/fecha especificado.")
    public List<Audit> auditList(
            @ApiParam(name = "date", type = "String", value = "Fecha a ser consultada en formato yyyy-MM-DD HH:mm",
                    required = true)
            @PathVariable String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
            return service.findAllInDate(dateTime);
        } catch (DateTimeParseException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e.getCause());
        }
    }
}
