package com.chickenfarms.escalationmanagement.model.payload;

import javax.validation.ValidationException;
import lombok.Getter;

@Getter
public class TicketUpdateRequest {

  private String description;
  private Long problem;

  public TicketUpdateRequest(String description, Long problem) {
    this.description = description;
    this.problem = problem;
    handleAllArgsNull();
  }

  private void handleAllArgsNull() {
    if(isDescriptionAndProblemMissing()){
        throw new ValidationException("description or problem is required");
    }
  }

  public boolean isDescriptionAndProblemMissing() {
    return description == null && problem == null;
  }

  public String getDescription() {
    if(description == null){
      return "";
    }
    return description;
  }
}

