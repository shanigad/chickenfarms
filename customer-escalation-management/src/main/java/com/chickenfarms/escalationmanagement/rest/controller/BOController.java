package com.chickenfarms.escalationmanagement.rest.controller;

import com.chickenfarms.escalationmanagement.model.dto.BORequest;
import com.chickenfarms.escalationmanagement.model.entity.Problem;
import com.chickenfarms.escalationmanagement.model.entity.Tag;
import com.chickenfarms.escalationmanagement.repository.ProblemRepository;
import com.chickenfarms.escalationmanagement.rest.service.ProblemService;
import com.chickenfarms.escalationmanagement.rest.service.TagService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BOController {

  private final ProblemService problemService;
  private final TagService tagService;

  @PostMapping("/problem")
  @ResponseStatus(HttpStatus.CREATED)
  public Problem createProblem(@Valid @RequestBody BORequest problem){
   return problemService.createProblem(problem);
  }

  @GetMapping("/problems")
  public List<Problem> getProblems(){
   return problemService.getProblems();
  }

  @PostMapping("/tag")
  @ResponseStatus(HttpStatus.CREATED)
  public Tag createTag(@Valid @RequestBody BORequest tag){
    return tagService.createTag(tag);
  }

}
