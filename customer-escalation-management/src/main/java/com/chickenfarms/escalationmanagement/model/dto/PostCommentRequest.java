package com.chickenfarms.escalationmanagement.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostCommentRequest {
  @NotBlank
  private String comment;
  @NotBlank
  private String createdBy;
}
