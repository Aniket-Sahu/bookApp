package com.aniket.newproject.controller;

import com.aniket.newproject.model.User;
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
}
