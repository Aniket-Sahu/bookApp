package com.aniket.newproject.controller;

import com.aniket.newproject.model.Story;
import com.aniket.newproject.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stories")
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;

    @GetMapping
    public ResponseEntity<List<Story>> getAllStories() {
        return ResponseEntity.ok(storyService.getAllStories());
    }

    @PostMapping
    public ResponseEntity<Story> create(@RequestBody Story story) {
        return ResponseEntity.ok(storyService.createStory(story));
    }

    @GetMapping("/genre/{genreName}")
    public ResponseEntity<List<Story>> getByGenre(@PathVariable String genreName) {
        return ResponseEntity.ok(storyService.getStoriesByGenre(genreName));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Story>> getByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(storyService.getStoriesByUser(userId));
    }

    @GetMapping("/{storyId}")
    public ResponseEntity<Story> getStory(@PathVariable UUID storyId) {
        return ResponseEntity.ok(storyService.getStoryById(storyId));
    }
}
