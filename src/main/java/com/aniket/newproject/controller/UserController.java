package com.aniket.newproject.controller;

import com.aniket.newproject.model.LoginRequest;
import com.aniket.newproject.model.User;
import com.aniket.newproject.model.Read;
import com.aniket.newproject.model.Story;
import com.aniket.newproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User createdUser = userService.register(user);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @PostMapping("/{followerId}/follow/{followeeId}")
    public ResponseEntity<String> followUser(
            @PathVariable UUID followerId,
            @PathVariable UUID followeeId
    ) {
        userService.followUser(followerId, followeeId);
        return ResponseEntity.ok("Followed successfully");
    }

    @PostMapping("/{followerId}/unfollow/{followeeId}")
    public ResponseEntity<String> unfollowUser(
            @PathVariable UUID followerId,
            @PathVariable UUID followeeId
    ) {
        userService.unfollowUser(followerId, followeeId);
        return ResponseEntity.ok("Unfollowed successfully");
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<User>> getFollowers(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getFollowers(userId));
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<List<User>> getFollowing(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getFollowing(userId));
    }

    @GetMapping("/{userId}/reads")
    public ResponseEntity<List<Read>> getUserReads(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getUserReads(userId));
    }

    @PostMapping("/{userId}/reads")
    public ResponseEntity<Read> addToReads(
            @PathVariable UUID userId,
            @RequestBody Read read) {
        read.getId().setUserId(userId);
        return ResponseEntity.ok(userService.addToReads(read));
    }

    @PutMapping("/{userId}/reads/{storyId}")
    public ResponseEntity<Read> updateReadingProgress(
            @PathVariable UUID userId,
            @PathVariable UUID storyId,
            @RequestBody Read read) {
        return ResponseEntity.ok(userService.updateReadingProgress(userId, storyId, read));
    }

    @DeleteMapping("/{userId}/reads/{storyId}")
    public ResponseEntity<?> removeFromReads(
            @PathVariable UUID userId,
            @PathVariable UUID storyId) {
        userService.removeFromReads(userId, storyId);
        return ResponseEntity.ok("Removed from reading list");
    }

    @GetMapping("/{userId}/likes")
    public ResponseEntity<List<Story>> getUserLikedStories(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getUserLikedStories(userId));
    }
}
