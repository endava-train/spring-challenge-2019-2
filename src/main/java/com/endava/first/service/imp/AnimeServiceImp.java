package com.endava.first.service.imp;

import com.endava.first.model.Anime;
import com.endava.first.repository.AnimeRepository;
import com.endava.first.service.AnimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AnimeServiceImp implements AnimeService {

    private final AnimeRepository animeRepository;
    private final MongoTemplate mongoTemplate;

    public List<Integer> getAll(final Optional<Integer> limit, final Optional<String> genre) {
        Query query = new Query();
        query.fields().include("anime_id");

        genre.ifPresent(s -> {
            String criteria = new StringBuilder(".*").append(s).append(".*").toString();
            query.addCriteria(Criteria.where("genre").regex(criteria));
        });
        limit.ifPresent(integer -> query.with(PageRequest.of(0, integer)));
        List<Anime> animes = mongoTemplate.find(query, Anime.class);
        return animes.stream().map(Anime::getAnimeId).collect(Collectors.toList());
    }

    public Optional<Anime> getByAnimeId(Integer animeId) {
        return animeRepository.findByAnimeId(animeId);
    }

    public List<Integer> getAllOrderedByRating(final Map<String, String> params) {
        Query query = new Query();
        query.fields().include("anime_id");
        query.with(new Sort(Sort.Direction.DESC, "rating"));

        if (params.containsKey("limit")) {
            query.with(PageRequest.of(0, Integer.parseInt(params.get("limit"))));
        } else {
            query.with(PageRequest.of(0, 100));
        }

        Stream.of("type", "studio", "source").forEach(field -> {
            if (params.containsKey(field)) {
                query.addCriteria(Criteria.where(field).is(params.get(field)));
            }
        });

        Stream.of("genre", "main_cast").forEach(field -> {
            if (params.containsKey(field)) {
                String criteria = new StringBuilder(".*").append(field).append(".*").toString();
                query.addCriteria(Criteria.where(field).regex(criteria));
            }
        });

        System.out.println(query);
        List<Anime> animes = mongoTemplate.find(query, Anime.class);
        return animes.stream().map(Anime::getAnimeId).collect(Collectors.toList());
    }
}
