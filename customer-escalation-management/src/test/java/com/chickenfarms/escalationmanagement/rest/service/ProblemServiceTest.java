package com.chickenfarms.escalationmanagement.rest.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.chickenfarms.escalationmanagement.exception.ResourceAlreadyExistException;
import com.chickenfarms.escalationmanagement.model.dto.BORequest;
import com.chickenfarms.escalationmanagement.model.entity.Problem;
import com.chickenfarms.escalationmanagement.repository.ProblemRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProblemServiceTest {

  @Mock
  private ProblemRepository problemRepository;
  @InjectMocks
  private ProblemService problemService;

  @Test
  void createProblemAlreadyExistTest() {
    Problem problem = new Problem();
    problem.setName("problem");
    problem.setId(1L);
    when(problemRepository.findProblemByName(anyString())).thenReturn(Optional.of(problem));
    assertThatExceptionOfType(ResourceAlreadyExistException.class).isThrownBy(() -> {
      problemService.createProblem(new BORequest("problem"));
    });
  }

  @Test
  void createProblemTest() {
    BORequest payload = new BORequest("test");
    Problem problem = new Problem();
    problem.setName(payload.getName());
    problem.setId(1L);
    when(problemRepository.findProblemByName(anyString())).thenReturn(Optional.empty());
    when(problemRepository.save(any(Problem.class))).thenReturn(problem);
    assertTrue(problemService.createProblem(payload).equals(problem));
  }
}
