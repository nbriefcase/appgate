package com.neirodiaz.appgate.repository;

import com.neirodiaz.appgate.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit, String> {

    @Query(value = "SELECT a FROM Audit a WHERE a.date >= :s")
    List<Audit> findAllInDate(@Param("s") LocalDateTime sessionData);
}
