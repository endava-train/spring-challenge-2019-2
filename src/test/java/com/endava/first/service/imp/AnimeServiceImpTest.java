package com.endava.first.service.imp;

import com.endava.first.model.Anime;
import com.endava.first.repository.AnimeRepository;
import com.endava.first.service.AnimeService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@Slf4j
public class AnimeServiceImpTest {


    private final List<Anime> animes = new ArrayList<>();

    @BeforeEach
    void eraseDataBase() {
        log.info("--> @BeforeEach - executes before each test method in this class");
    }

    @Test
    public void getAllAnimeId() {
        AnimeRepository animeRepository = mock(AnimeRepository.class);
        MongoTemplate mongoTemplate = mock(MongoTemplate.class);

        List<Anime> animes = Stream
                .iterate(0, i -> i + 1)
                .limit(10)
                .map(animeId -> {
                    Anime anime = new Anime();
                    anime.setAnimeId(animeId);
                    return anime;
                }).collect(Collectors.toList());

        List<Integer> animeList = animes
                .stream()
                .map(Anime::getAnimeId)
                .collect(Collectors.toList());

        when(mongoTemplate.find(any(Query.class), any(Class.class))).thenReturn(animes);
        AnimeService animeService = new AnimeServiceImp(animeRepository, mongoTemplate);
        val allAnimeIds = animeService.getAllAnimeId(Optional.empty(), Optional.empty());
        assertEquals(allAnimeIds.toString(), animeList.toString());
    }

    @Test
    public void getByAnimeId() {
        AnimeRepository animeRepository = mock(AnimeRepository.class);
        MongoTemplate mongoTemplate = mock(MongoTemplate.class);

        List<Anime> animes = Stream
                .iterate(0, i -> i + 1)
                .limit(new Random().nextInt(10) + 2)
                .map(animeId -> {
                    Anime anime = new Anime();
                    anime.setAnimeId(animeId);
                    return anime;
                }).collect(Collectors.toList());

        int chosenIndex = new Random().nextInt(animes.size());
        Anime chosenElement = animes.get(chosenIndex);
        when(animeRepository.findByAnimeId(any(Integer.class))).thenReturn(Optional.of(chosenElement));
        AnimeService animeService = new AnimeServiceImp(animeRepository, mongoTemplate);

        val anime = animeService.getByAnimeId(chosenIndex);
        assertEquals(anime.get().getAnimeId(), chosenElement.getAnimeId());
    }

    @Test
    public void getAllOrderedByRating() {
        AnimeRepository animeRepository = mock(AnimeRepository.class);
        MongoTemplate mongoTemplate = mock(MongoTemplate.class);

        List<Anime> animes = Stream
                .iterate(0, i -> i + 1)
                .limit(10)
                .map(animeId -> {
                    Anime anime = new Anime();
                    anime.setAnimeId(animeId);
                    return anime;
                }).collect(Collectors.toList());

        List<Integer> animeList = animes
                .stream()
                .map(Anime::getAnimeId)
                .collect(Collectors.toList());

        when(mongoTemplate.find(any(Query.class), any(Class.class))).thenReturn(animes);
        AnimeService animeService = new AnimeServiceImp(animeRepository, mongoTemplate);
        val allOrderedByRating = animeService.getAllOrderedByRating(new TreeMap<>());
        assertEquals(allOrderedByRating.toString(), animeList.toString());
    }

}