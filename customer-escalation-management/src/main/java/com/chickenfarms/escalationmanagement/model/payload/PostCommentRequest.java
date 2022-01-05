package com.chickenfarms.escalationmanagement.model.payload;

import javax.validation.constraints.NotBlank;
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
