package com.chickenfarms.escalationmanagement.model.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BORequest {
  @NotBlank(message = "name must not be empty")
  private String name;

}

