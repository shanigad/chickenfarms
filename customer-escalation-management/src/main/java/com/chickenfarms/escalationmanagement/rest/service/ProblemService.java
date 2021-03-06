package com.chickenfarms.escalationmanagement.rest.service;

import com.chickenfarms.escalationmanagement.exception.ResourceAlreadyExistException;
import com.chickenfarms.escalationmanagement.exception.ResourceNotFoundException;
import com.chickenfarms.escalationmanagement.model.payload.CreateCommentRequest;
import com.chickenfarms.escalationmanagement.model.entity.Problem;
import com.chickenfarms.escalationmanagement.repository.ProblemRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemService {

  private final ProblemRepository problemRepository;

  public Problem createProblem(CreateCommentRequest createCommentRequest){
    handleDuplicateProblem(createCommentRequest.getName());
    Problem problem = new Problem();
    problem.setName(createCommentRequest.getName());
    return saveProblem(problem);
  }

  public Problem getProblemIfExist(Long id){
    return problemRepository.findById(id).
        orElseThrow(()-> new ResourceNotFoundException("Problem", "id", String.valueOf(id)));
  }

  public Problem getProblemOrNull(Long problem) {
    return problem!= null ? getProblemIfExist(problem) : null;
  }

  private Problem saveProblem(Problem problem) {
    problem = problemRepository.save(problem);
    problemRepository.flush();
    return problem;
  }

  private void handleDuplicateProblem(String name) {
    Optional<Problem> problem =  problemRepository.findProblemByName(name);
    if(problem.isPresent()) {
      throw new ResourceAlreadyExistException("Problem",  Long.valueOf(problem.get().getId()));
    }
  }



}
