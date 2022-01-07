package com.chickenfarms.escalationmanagement.rest.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.chickenfarms.escalationmanagement.model.entity.Comment;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.model.payload.PostCommentRequest;
import com.chickenfarms.escalationmanagement.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

  @Mock
  private CommentRepository commentRepository;
  @InjectMocks
  private CommentService commentService;


  @Test
  void postCommentTest() {
    PostCommentRequest postCommentRequest = new PostCommentRequest("I'm a comment", "Commenter");
    Comment expectedComment = new Comment(postCommentRequest);
    when(commentRepository.save(any(Comment.class))).thenReturn(expectedComment);
    Comment actualComment = commentService.postComment(postCommentRequest, new Ticket());
    assertTrue(expectedComment.getContent().equals(actualComment.getContent()));
    assertTrue(expectedComment.getCreatedBy().equals(actualComment.getCreatedBy()));
  }

  @Test
  void getTicketComments() {
  }
}
