package com.content.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
@AllArgsConstructor
public class UpdateCategoryNameRequest {

    @NonNull
    private String categoryId;

    @NonNull
    private String name;
}
