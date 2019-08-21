package com.endava.first.repository;

import com.endava.first.model.Anime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AnimeRepository extends MongoRepository<Anime, Integer>, PagingAndSortingRepository<Anime, Integer> {
    Optional<Anime> findByAnimeId(Integer animeId);
}
