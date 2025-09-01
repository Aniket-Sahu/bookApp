package com.aniket.newproject.service;

import com.aniket.newproject.model.*;
import com.aniket.newproject.repo.FollowRepository;
import com.aniket.newproject.repo.UserRepo;
import com.aniket.newproject.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    @Autowired
    private UserRepo repo;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

//    public User saveUser(User user) {
//        user.setPassword(encoder.encode(user.getPassword()));
//        System.out.println(user.getPassword());
//        return repo.save(user) ;
//    }

    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    @Transactional
    public void followUser(UUID followerId, UUID followeeId) {
        if (followerId.equals(followeeId)) {
            throw new IllegalArgumentException("User cannot follow themselves");
        }

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower not found"));
        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new RuntimeException("User to follow not found"));

        FollowId followId = new FollowId(followerId, followeeId);
        if (!followRepository.existsById(followId)) {
            Follow follow = new Follow(followId, follower, followee, LocalDateTime.now());
            followRepository.save(follow);
        }
    }

    @Transactional
    public void unfollowUser(UUID followerId, UUID followeeId) {
        FollowId followId = new FollowId(followerId, followeeId);
        followRepository.deleteById(followId);
    }

    public List<User> getFollowers(UUID userId) {
        return followRepository.findAllByIdFollowingId(userId)
                .stream()
                .map(Follow::getFollower)
                .collect(Collectors.toList());
    }

    public List<User> getFollowing(UUID userId) {
        return followRepository.findAllByIdFollowerId(userId)
                .stream()
                .map(Follow::getFollowing)
                .collect(Collectors.toList());
    }
}
