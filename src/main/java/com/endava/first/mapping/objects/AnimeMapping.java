package com.endava.first.mapping.objects;

import com.endava.first.model.Anime;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AnimeMapping {

    @Field("anime_id")
    @JsonProperty("anime_id")
    private Integer animeId;

    private String name;

    private List<String> genre;

    private String type;

    private Integer episodes;

    private Double rating;

    private List<String> studios;

    private String source;

    @Field("main_cast")
    @JsonProperty("main_cast")
    private List<String> mainCast;

    public AnimeMapping(Anime anime) {
        this.animeId = anime.getAnimeId();
        this.name = anime.getName();
        this.genre = Arrays.stream(anime.getGenre().split(",")).collect(Collectors.toList());
        this.type = anime.getType();
        this.episodes = anime.getEpisodes();
        this.rating = anime.getRating();
        this.studios = Arrays.stream(anime.getStudios().split(",")).collect(Collectors.toList());
        this.source = anime.getSource();
        this.mainCast = Arrays.stream(anime.getMainCast().split(",")).collect(Collectors.toList());
    }
}
