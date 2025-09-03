package com.aniket.newproject.controller;

import com.aniket.newproject.model.Story;
import com.aniket.newproject.model.Chapter;
import com.aniket.newproject.service.StoryService;
import com.aniket.newproject.service.NotificationService;
import com.aniket.newproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stories")
@RequiredArgsConstructor
public class StoryController {

    private final NotificationService notificationService;
    private final StoryService storyService;
    private final UserService userService;

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

    @GetMapping("/search")
    public ResponseEntity<List<Story>> searchStories(@RequestParam String query) {
        return ResponseEntity.ok(storyService.searchStories(query));
    }

    @PostMapping("/{storyId}/like")
    public ResponseEntity<?> likeStory(@PathVariable UUID storyId, @RequestParam UUID userId) {
        Story story = storyService.getStoryById(storyId);
        // Create notification for story author
        notificationService.createLikeNotification(
                userId,
                storyId,
                story.getAuthor().getId(),
                userService.findById(userId).getUsername(),
                story.getTitle()
        );
        return ResponseEntity.ok("Story liked");
    }


    @GetMapping("/{storyId}/chapters")
    public ResponseEntity<List<Chapter>> getChapters(@PathVariable UUID storyId) {
        return ResponseEntity.ok(storyService.getChaptersByStoryId(storyId));
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
