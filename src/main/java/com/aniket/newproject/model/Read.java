package com.aniket.newproject.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Read {
    @EmbeddedId
    private ReadId id;

    @ManyToOne @MapsId("userId")
    private User user;

    @ManyToOne @MapsId("storyId")
    private Story story;

    @ManyToOne
    private Chapter lastChapterRead;

    @Enumerated(EnumType.STRING)
    private ReadStatus status;

    private LocalDateTime updatedAt;
}

