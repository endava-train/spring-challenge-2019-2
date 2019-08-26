package com.endava.first.repository;

import com.endava.first.model.Anime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RestResource(exported = false)
public interface AnimeRepository extends MongoRepository<Anime, Integer>, PagingAndSortingRepository<Anime, Integer> {
    Optional<Anime> findByAnimeId(final int animeId);
}
