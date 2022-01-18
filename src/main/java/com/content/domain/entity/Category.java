package com.content.domain.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Document
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Category {

    @Id
    @Getter
    private String id;

    @NonNull
    @Getter
    private String name;

    @Getter
    private Set<Long> videosIds = new HashSet<>();

    private Category(@NonNull String name, Set<Long> videosIds) {
        this.name = name;
        this.videosIds.addAll(videosIds);
    }

    public static Category create(String name, Set<Long> videosIds) {
        return new Category(name, videosIds);
    }

    public Category addVideos(Set<Long> videosIds) {
        this.videosIds.addAll(videosIds);
        return this;
    }

    public Category removeVideos(Set<Long> videosIds) {
        this.videosIds.removeAll(videosIds);
        return this;
    }

    public Category changeName(String name) {
        this.name = name;
        return this;
    }

}
