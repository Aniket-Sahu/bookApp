package com.aniket.newproject.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @Id @GeneratedValue
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "genre")
    private List<Story> stories;
}

