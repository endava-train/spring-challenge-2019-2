package com.endava.first.controller;

import com.endava.first.mapping.objects.AnimeMapping;
import com.endava.first.model.Anime;
import com.endava.first.service.AnimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AnimeController {
    private final AnimeService animeService;

    @GetMapping("anime")
    public ResponseEntity<List<Integer>> getAllAnimeId(
            final @RequestParam Optional<Integer> limit,
            final @RequestParam Optional<String> genre
        ) {
        return ResponseEntity.ok(animeService.getAllAnimeId(limit, genre));
    }

    @GetMapping("anime/{animeId}")
    public ResponseEntity<AnimeMapping> getByAnimeId(final @PathVariable("animeId") Integer animeId) {
        Optional<AnimeMapping> animeMapping = animeService.getByAnimeId(animeId);
        return animeMapping.isPresent() ? ResponseEntity.ok(animeMapping.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("anime/top")
    public ResponseEntity<List<Integer>> getAllOrderedByRating(final @RequestParam Map<String, String> params) {
        return ResponseEntity.ok(animeService.getAllOrderedByRating(params));
    }

}
