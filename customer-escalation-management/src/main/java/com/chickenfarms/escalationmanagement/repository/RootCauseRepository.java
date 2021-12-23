package com.chickenfarms.escalationmanagement.repository;

import com.chickenfarms.escalationmanagement.model.entity.RootCause;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RootCauseRepository extends JpaRepository<RootCause, Integer> {
  public List<RootCause> findRootCauseById(int rootCauseId);
}
