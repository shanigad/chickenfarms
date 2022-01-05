package com.chickenfarms.escalationmanagement.rest.service;


import com.chickenfarms.escalationmanagement.exception.ResourceAlreadyExistException;
import com.chickenfarms.escalationmanagement.model.entity.Tag;
import com.chickenfarms.escalationmanagement.repository.TagRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

  private final TagRepository tagRepository;

  public Tag createTag(String newTagName){
    checkIfTagExist(newTagName);
    Tag tag = new Tag();
    tag.setName(newTagName);
    return saveToRepository(tag);
  }

  public Tag getTagOrCreateIfMissing(String tagName){
    Optional<Tag> tag = tagRepository.findTagByName(tagName);
    if(!tag.isPresent()){
      return createTag(tagName);
    }
    return tag.get();
  }

  private void checkIfTagExist(String newTagName) {
    Optional<Tag> tag = tagRepository.findTagByName(newTagName);
    if(tag.isPresent()){
      throw new ResourceAlreadyExistException("Tag", tag.get().getId());
    }
  }

  private Tag saveToRepository(Tag tag){
    tag = tagRepository.save(tag);
    tagRepository.flush();
    return tag;
  }

}
