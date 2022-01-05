package com.chickenfarms.escalationmanagement.repository;

import com.chickenfarms.escalationmanagement.model.entity.RootCause;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RootCauseRepository extends JpaRepository<RootCause, Long> {
}
