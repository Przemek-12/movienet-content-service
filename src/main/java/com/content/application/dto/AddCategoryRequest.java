package com.content.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Builder
@Getter
public class AddCategoryRequest {

    @NonNull
    private String viewId;

    @NonNull
    private AddCategoryDTO addCategoryDTO;

}
