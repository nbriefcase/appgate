package com.neirodiaz.appgate.service;

import com.neirodiaz.appgate.model.Backlog;
import com.neirodiaz.appgate.model.SessionData;

import java.util.List;
import java.util.UUID;

public interface CalculatorService extends BaseService {

    /**
     * Crea una sesión.
     *
     * @return UUID de la sesión creada
     */
    UUID createSession();

    /**
     * Consulta una sesion.
     *
     * @param sessionId Identificador de la sesión.
     * @return Los datos de uns sesión
     */
    SessionData getSessionById(String sessionId);

    /**
     * Consulta todas las sesiones que estén en curso.
     *
     * @return Lista de "SessionData" con información sobre creacion y última modificación.
     */
    List<SessionData> getSessionList();

    /**
     * Elimina una sesión específica.
     *
     * @param sessionId Identificador de la sesión.
     */
    void deleteSession(String sessionId);

    /**
     * Agrega un operando a una sesión
     *
     * @param sessionId Identificador de la sesión.
     * @param operand   Operación aritmética a realizar.
     * @return Un objeto tipo Backlog con el operando agregado a la sesión.
     */
    Backlog addOperand(String sessionId, Double operand);

    /**
     * Ejecuta la operación aritmética para una sesión específica.
     *
     * @param sessionId Identificador de la sesión.
     * @param action    Operación aritmética a realizar.
     * @return El resultado de la oparación.
     */
    Double calculate(String sessionId, String action);
}
