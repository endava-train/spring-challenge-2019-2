package com.endava.first.service.imp;

import com.endava.first.mapping.objects.AnimeMapping;
import com.endava.first.model.Anime;
import com.endava.first.repository.AnimeRepository;
import com.endava.first.service.AnimeService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AnimeServiceImpTest {

    @Autowired
    private AnimeService animeService;
    @Autowired
    private AnimeRepository animeRepository;

    @BeforeEach
    void eraseDataBase() {
        log.info("--> @BeforeEach - executes before each test method in this class");
    }

    @Test
    public void getAllIds() {
        animeRepository.deleteAll();
        List<Anime> animes = Stream
                .iterate(0, i -> i + 1)
                .limit(new Random().nextInt(10))
                .map(animeId -> {
                    Anime anime = new Anime();
                    anime.setAnimeId(animeId);
                    return anime;
                }).collect(Collectors.toList());

        animes.forEach(anime -> animeRepository.save(anime));

        val animeList = animes
                .stream()
                .map(Anime::getAnimeId)
                .collect(Collectors.toList());

        val idsAnimes = animeService.getAll(Optional.empty(), Optional.empty());

        assertEquals(idsAnimes.toString(), animeList.toString());
    }

    @Test
    public void getByAnimeId() {
        animeRepository.deleteAll();
        List<Anime> animes = Stream
                .iterate(0, i -> i + 1)
                .limit(new Random().nextInt(10) + 1)
                .map(animeId -> {
                    Anime anime = new Anime();
                    anime.setAnimeId(animeId);
                    return anime;
                }).collect(Collectors.toList());

        animes.forEach(anime -> animeRepository.save(anime));

        int randomId = new Random().nextInt(animes.size());
        log.info(Integer.valueOf(randomId).toString());
        log.info(animeRepository.findAll().toString());
        log.info(animeService.getByAnimeId(randomId).toString());
//        Optional<AnimeMapping> anime = animeService.getByAnimeId(randomId);
//        log.info(anime.toString());
//        assertEquals(anime.toString(), new AnimeMapping(animes.get(randomId)).toString());
    }
}