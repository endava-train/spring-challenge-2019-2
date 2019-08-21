package com.endava.first.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@Document(collection = "CTF")
public class Anime {

    @Id
    private String id;

    @Field("anime_id")
    @JsonProperty("anime_id")
    private Integer animeId;

    private String name;

    private String genre;

    private String type;

    private Integer episodes;

    private Double rating;

    private String img;

    private String studios;

    private String source;

    @Field("main_cast")
    @JsonProperty("main_cast")
    private String mainCast;

    private Integer c1;

    private Integer c2;

    private Integer members;
}
