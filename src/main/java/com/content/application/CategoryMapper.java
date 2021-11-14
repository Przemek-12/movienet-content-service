package com.content.application;

import com.content.application.dto.AddCategoryDTO;
import com.content.application.dto.CategoryDTO;
import com.content.domain.entity.Category;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper {

    public static CategoryDTO mapToDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .videosIds(category.getVideosIds())
                .build();
    }

    public static Category mapToCategory(AddCategoryDTO addCategoryDTO) {
        return Category.create(
                addCategoryDTO.getName(),
                addCategoryDTO.getVideosIds());
    }

}
