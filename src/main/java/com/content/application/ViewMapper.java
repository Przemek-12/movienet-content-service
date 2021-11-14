package com.content.application;

import java.util.stream.Collectors;

import com.content.application.dto.ViewDTO;
import com.content.domain.entity.View;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ViewMapper {

    public static ViewDTO mapToDTO(View view) {
        return ViewDTO.builder()
                .id(view.getId())
                .name(view.getName())
                .categories(view.getCategories().stream()
                        .map(category -> CategoryMapper.mapToDTO(category))
                        .collect(Collectors.toSet()))
                .build();
    }
}
