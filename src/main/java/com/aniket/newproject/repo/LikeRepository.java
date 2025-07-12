package com.aniket.newproject.repo;

import com.aniket.newproject.model.Like;
import com.aniket.newproject.model.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
}
