package com.chickenfarms.escalationmanagement.rest.service;

import com.chickenfarms.escalationmanagement.model.payload.PostCommentRequest;
import com.chickenfarms.escalationmanagement.model.payload.TicketComments;
import com.chickenfarms.escalationmanagement.model.entity.Comment;
import com.chickenfarms.escalationmanagement.model.entity.Ticket;
import com.chickenfarms.escalationmanagement.repository.CommentRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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


  public TicketComments getTicketComments(Ticket ticket, int page){
    Page<Comment> commentsPage = commentRepository.getAllByTicket(ticket, PageRequest.of(page, 5));
    List<String> comments = new ArrayList<>();
    commentsPage.forEach(comment -> comments.add(comment.toString()));
    return new TicketComments(ticket.getId(),page, comments);
  }

}
