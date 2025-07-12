package com.aniket.newproject.service;

import com.aniket.newproject.model.*;
import com.aniket.newproject.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReadService {

    private final ReadRepository readRepository;
    private final UserRepository userRepository;
    private final StoryRepository storyRepository;

    public Read updateReadStatus(UUID userId, UUID storyId, ReadStatus status) {
        User user = userRepository.findById(userId).orElseThrow();
        Story story = storyRepository.findById(storyId).orElseThrow();

        ReadId id = new ReadId(userId, storyId);
        Read read = readRepository.findById(id).orElse(
                new Read(id, user, story, null, status, LocalDateTime.now())
        );

        read.setStatus(status);
        read.setUpdatedAt(LocalDateTime.now());

        return readRepository.save(read);
    }

    public List<Read> getReadsByStatus(UUID userId, ReadStatus status) {
        return readRepository.findByUserIdAndStatus(userId, status);
    }
}
