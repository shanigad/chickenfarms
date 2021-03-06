package com.chickenfarms.escalationmanagement.model.payload;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateCommentRequest {
  @NotBlank(message = "name must not be empty")
  private String name;

}

