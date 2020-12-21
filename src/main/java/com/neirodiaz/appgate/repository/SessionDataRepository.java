package com.neirodiaz.appgate.repository;

import com.neirodiaz.appgate.model.SessionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionDataRepository extends JpaRepository<SessionData, String> {
}
