package com.chickenfarms.escalationmanagement.rest.service;


import com.chickenfarms.escalationmanagement.model.dto.BORequest;
import com.chickenfarms.escalationmanagement.model.entity.Tag;
import com.chickenfarms.escalationmanagement.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

  private final TagRepository tagRepository;

  public Tag createTag(BORequest newTag){
    Tag tag = new Tag();
    tag.setName(newTag.getName());
    tag = tagRepository.save(tag);
    tagRepository.flush();
    return tag;
  }


}
