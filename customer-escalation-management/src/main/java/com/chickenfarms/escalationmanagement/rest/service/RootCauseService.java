package com.chickenfarms.escalationmanagement.rest.service;

import com.chickenfarms.escalationmanagement.exception.ResourceNotFoundException;
import com.chickenfarms.escalationmanagement.model.entity.RootCause;
import com.chickenfarms.escalationmanagement.repository.RootCauseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RootCauseService {

  private final RootCauseRepository rootCauseRepository;

  public RootCause getRootCauseIfExist(Long id){
    return rootCauseRepository.findById(id).
        orElseThrow(()-> new ResourceNotFoundException("RootCause", "id", String.valueOf(id
        )));
  }
}
