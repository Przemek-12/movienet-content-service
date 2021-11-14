package com.content.application.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class AddCategoryDTO {

    @NonNull
    private String name;

    private Set<Long> videosIds = new HashSet<>();

    public AddCategoryDTO(@NonNull String name, Set<Long> videosIds) {
        this.name = name;
        if (videosIds != null) {
            this.videosIds = videosIds;
        }
    }


}
