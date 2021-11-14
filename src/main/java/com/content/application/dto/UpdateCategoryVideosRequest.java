package com.content.application.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class UpdateCategoryVideosRequest {

    @NonNull
    private String categoryId;

    private Set<Long> videosIds = new HashSet<>();

    public UpdateCategoryVideosRequest(@NonNull String categoryId, Set<Long> videosIds) {
        this.categoryId = categoryId;
        if (videosIds != null) {
            this.videosIds = videosIds;
        }
    }


}
