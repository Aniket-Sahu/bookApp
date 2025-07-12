package com.aniket.newproject.repo;

import com.aniket.newproject.model.Chapter;
import com.aniket.newproject.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChapterRepository extends JpaRepository<Chapter, UUID> {
    List<Chapter> findByStory(Story story);
}
