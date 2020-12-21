package com.neirodiaz.appgate.repository;

import com.neirodiaz.appgate.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<Audit, String> {
}
