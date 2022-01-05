package com.chickenfarms.escalationmanagement.rest.service;

import com.chickenfarms.escalationmanagement.model.dto.PostCommentRequest;
import com.chickenfarms.escalationmanagement.model.entity.Comment;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.repository.CommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;

  public Comment postComment(PostCommentRequest postCommentRequest, Ticket ticket){
    // if Ticket not null
    //handle duplicateComment?
    Comment comment = new Comment(postCommentRequest);
    comment.setTicket(ticket);
    comment = commentRepository.save(comment);
    commentRepository.flush();
    return comment;
  }


}
