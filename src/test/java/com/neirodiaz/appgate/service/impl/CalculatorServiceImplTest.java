package com.neirodiaz.appgate.service.impl;

import com.neirodiaz.appgate.exception.BacklogNotFoundException;
import com.neirodiaz.appgate.exception.CalculatorException;
import com.neirodiaz.appgate.model.Backlog;
import com.neirodiaz.appgate.model.SessionData;
import com.neirodiaz.appgate.model.type.Action;
import com.neirodiaz.appgate.repository.BacklogRepository;
import com.neirodiaz.appgate.repository.SessionDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.doReturn;

class CalculatorServiceImplTest {

    @Mock
    private SessionDataRepository sessionRepository;

    @Mock
    private BacklogRepository backlogRepository;

    @InjectMocks
    private CalculatorServiceImpl service = new CalculatorServiceImpl(sessionRepository, backlogRepository);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        String id = UUID.randomUUID().toString();
        Optional<SessionData> optionalSession = Optional.of(SessionData.builder()
                .id(id)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build());

        doReturn(optionalSession).when(sessionRepository).findById(id);
        localSession = optionalSession.get();
    }

    private SessionData localSession;

    @Test
    void calculatorAddOperationSuccess() {
        List<Backlog> backlogList = new ArrayList<>();
        double esperado = 0;

        // Agregar operandos
        for (int i = 1; i < 6; i++) {
            backlogList.add(service.addOperand(localSession.getId(), (double) i));
            esperado += i;
        }
        doReturn(backlogList).when(backlogRepository).findAllBySession(localSession);
        Double resultado = service.calculate(localSession.getId(), Action.suma.toString());

        Assertions.assertEquals(esperado, resultado);
    }

    @Test
    void calculatorSubtractOperationSuccess() {
        List<Backlog> backlogList = new ArrayList<>();
        // Agregar operandos
        backlogList.add(service.addOperand(localSession.getId(), 500d));
        backlogList.add(service.addOperand(localSession.getId(), 100d));
        backlogList.add(service.addOperand(localSession.getId(), 150d));
        backlogList.add(service.addOperand(localSession.getId(), 250d));

        doReturn(backlogList).when(backlogRepository).findAllBySession(localSession);
        Double resultado = service.calculate(localSession.getId(), Action.resta.toString());

        Assertions.assertEquals(0d, resultado);
    }

    @Test
    void calculatorMultiplicationOperationSuccess() {
        List<Backlog> backlogList = new ArrayList<>();
        // Agregar operandos
        backlogList.add(service.addOperand(localSession.getId(), 1d));
        backlogList.add(service.addOperand(localSession.getId(), 2d));
        backlogList.add(service.addOperand(localSession.getId(), 3d));
        backlogList.add(service.addOperand(localSession.getId(), 4d));

        doReturn(backlogList).when(backlogRepository).findAllBySession(localSession);
        Double resultado = service.calculate(localSession.getId(), Action.multiplicacion.toString());

        Assertions.assertEquals(24d, resultado);
    }

    @Test
    void calculatorDivisionOperationSuccess() {
        List<Backlog> backlogList = new ArrayList<>();
        // Agregar operandos
        backlogList.add(service.addOperand(localSession.getId(), 100d));
        backlogList.add(service.addOperand(localSession.getId(), 5d));
        backlogList.add(service.addOperand(localSession.getId(), 4d));
        backlogList.add(service.addOperand(localSession.getId(), 5d));

        doReturn(backlogList).when(backlogRepository).findAllBySession(localSession);
        Double resultado = service.calculate(localSession.getId(), Action.division.toString());

        Assertions.assertEquals(1d, resultado);
    }

    @Test
    void calculatorDivisionOperationFails() {
        List<Backlog> backlogList = new ArrayList<>();
        // Agregar operandos
        backlogList.add(service.addOperand(localSession.getId(), 100d));
        backlogList.add(service.addOperand(localSession.getId(), 0d));
        backlogList.add(service.addOperand(localSession.getId(), 4d));
        backlogList.add(service.addOperand(localSession.getId(), 5d));

        doReturn(backlogList).when(backlogRepository).findAllBySession(localSession);
        Double resultado = service.calculate(localSession.getId(), Action.division.toString());

        Assertions.assertTrue(Double.isInfinite(resultado));
    }

    @Test
    void calculatorPowOperationFails() {
        List<Backlog> backlogList = new ArrayList<>();
        // Agregar operandos
        backlogList.add(service.addOperand(localSession.getId(), 100d));
        backlogList.add(service.addOperand(localSession.getId(), 999d));
        backlogList.add(service.addOperand(localSession.getId(), 4d));
        backlogList.add(service.addOperand(localSession.getId(), 5d));

        doReturn(backlogList).when(backlogRepository).findAllBySession(localSession);
        Double resultado = service.calculate(localSession.getId(), Action.potenciacion.toString());

        Assertions.assertTrue(Double.isInfinite(resultado));
    }

    @Test
    void calculatorPowOperationSuccess() {
        List<Backlog> backlogList = new ArrayList<>();
        // Agregar operandos
        backlogList.add(service.addOperand(localSession.getId(), 2d));
        backlogList.add(service.addOperand(localSession.getId(), 3d));
        backlogList.add(service.addOperand(localSession.getId(), 4d));
        backlogList.add(service.addOperand(localSession.getId(), 2d));

        doReturn(backlogList).when(backlogRepository).findAllBySession(localSession);
        Double resultado = service.calculate(localSession.getId(), Action.potenciacion.toString());

        Assertions.assertEquals(16777216d, resultado);
    }

    @Test
    void calculatorOperationsFailsWithoutOperands() {
        List<Backlog> backlogList = new ArrayList<>();
        // Realizar el mock de las dependencias.
        doReturn(backlogList).when(backlogRepository).findAllBySession(localSession);

        for (Action accion : EnumSet.allOf(Action.class)) {
            Assertions.assertThrows(
                    BacklogNotFoundException.class,
                    () -> service.calculate(localSession.getId(), accion.toString()));
        }
    }

    @Test
    void calculatorOperationsFailsWithOnlyOneOperands() {
        List<Backlog> backlogList = new ArrayList<>();
        // Agregar operandos
        backlogList.add(service.addOperand(localSession.getId(), 10d));
        doReturn(backlogList).when(backlogRepository).findAllBySession(localSession);

        for (Action accion : EnumSet.allOf(Action.class)) {
            Assertions.assertThrows(
                    CalculatorException.class,
                    () -> service.calculate(localSession.getId(), accion.toString()));
        }
    }
}