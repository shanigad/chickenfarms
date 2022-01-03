package com.chickenfarms.escalationmanagement.rest.service;

import com.chickenfarms.escalationmanagement.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemService {

  ProblemRepository problemRepository;

  public ProblemService(
      ProblemRepository problemRepository) {
    this.problemRepository = problemRepository;
  }

}
