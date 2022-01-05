package com.chickenfarms.escalationmanagement.repository;

import com.chickenfarms.escalationmanagement.model.entity.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
  Optional<Tag> findTagByName(String name);
}
