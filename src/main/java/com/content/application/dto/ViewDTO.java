package com.content.application.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ViewDTO {

    private String id;
    private String name;
    private Set<CategoryDTO> categories;
}
