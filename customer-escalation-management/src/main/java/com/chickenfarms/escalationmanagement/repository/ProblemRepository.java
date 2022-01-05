package com.chickenfarms.escalationmanagement.repository;

import com.chickenfarms.escalationmanagement.model.entity.Problem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
  Optional<Problem> findProblemByName(String name);
}
