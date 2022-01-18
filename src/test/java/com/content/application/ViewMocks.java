package com.content.application;

import java.util.HashSet;
import java.util.Set;

import com.content.application.dto.AddCategoryRequest;
import com.content.application.dto.AddViewRequest;
import com.content.domain.entity.Category;
import com.content.domain.entity.View;

public class ViewMocks {

    public static AddViewRequest addViewRequestMock() {
        return AddViewRequest.builder()
                .name("name")
                .categories(Set.of(
                        CategoryMocks.addCategoryDTOMock()))
                .build();
    }

    public static AddCategoryRequest addCategoryRequestMock() {
        return AddCategoryRequest.builder()
                .addCategoryDTO(CategoryMocks.addCategoryDTOMock())
                .viewId("id")
                .build();
    }

    public static View viewMock() {
        Set<Category> cats = new HashSet<>();
        cats.add(CategoryMocks.categoryMock());
        return View.create("name", cats);
    }

}
