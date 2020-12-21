package com.neirodiaz.appgate.repository;

import com.neirodiaz.appgate.model.Backlog;
import com.neirodiaz.appgate.model.SessionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BacklogRepository extends JpaRepository<Backlog, String> {
    List<Backlog> findAllBySession(SessionData sessionData);
}
