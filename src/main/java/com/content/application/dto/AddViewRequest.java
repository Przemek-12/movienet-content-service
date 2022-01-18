package com.content.application.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class AddViewRequest {

    @NonNull
    private String name;

    private Set<AddCategoryDTO> categories = new HashSet<>();

    @Builder
    public AddViewRequest(@NonNull String name, Set<AddCategoryDTO> categories) {
        this.name = name;
        if (categories != null) {
            this.categories = categories;
        }
    }

}
