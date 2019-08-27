package com.endava.first.service.imp;

import com.endava.first.model.Anime;
import com.endava.first.repository.AnimeRepository;
import com.endava.first.service.AnimeService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;
import org.junit.Before;
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

    private List<Anime> animes;
    private List<Integer> animeList;
    private AnimeRepository animeRepository;
    private MongoTemplate mongoTemplate;

    @Before
    public void before() {
        animeRepository = mock(AnimeRepository.class);
        mongoTemplate = mock(MongoTemplate.class);

        animes = Stream
                .iterate(0, i -> i + 1)
                .limit(10)
                .map(animeId -> {
                    Anime anime = new Anime();
                    anime.setAnimeId(animeId);
                    return anime;
                }).collect(Collectors.toList());

        animeList = animes
                .stream()
                .map(Anime::getAnimeId)
                .collect(Collectors.toList());

    }

    @Test
    public void getAllAnimeId() {
        when(mongoTemplate.find(any(Query.class), any(Class.class))).thenReturn(animes);
        AnimeService animeService = new AnimeServiceImp(animeRepository, mongoTemplate);
        val allAnimeIds = animeService.getAllAnimeId(Optional.empty(), Optional.empty());
        assertEquals(allAnimeIds.toString(), animeList.toString());
    }

    @Test
    public void getByAnimeId() {
        int chosenIndex = new Random().nextInt(animes.size());
        Anime chosenElement = animes.get(chosenIndex);
        when(animeRepository.findByAnimeId(any(Integer.class))).thenReturn(Optional.of(chosenElement));
        AnimeService animeService = new AnimeServiceImp(animeRepository, mongoTemplate);

        val anime = animeService.getByAnimeId(chosenIndex);
        assertEquals(anime.get().getAnimeId(), chosenElement.getAnimeId());
    }

    @Test
    public void getAllOrderedByRating() {
        when(mongoTemplate.find(any(Query.class), any(Class.class))).thenReturn(animes);
        AnimeService animeService = new AnimeServiceImp(animeRepository, mongoTemplate);
        val allOrderedByRating = animeService.getAllOrderedByRating(new TreeMap<>());
        assertEquals(allOrderedByRating.toString(), animeList.toString());
    }

}