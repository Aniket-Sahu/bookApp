package com.aniket.newproject.service;

import com.aniket.newproject.model.Genre;
import com.aniket.newproject.model.Story;
import com.aniket.newproject.repo.GenreRepository;
import com.aniket.newproject.repo.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final StoryRepository storyRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public List<Story> getStoriesByGenreName(String genreName) {
        Genre genre = genreRepository.findByName(genreName)
                .orElseThrow(() -> new RuntimeException("Genre not found"));

        return storyRepository.findByGenre(genre);
    }
}
