package com.content.application;

import java.util.HashSet;
import java.util.Set;

import com.content.application.dto.AddCategoryDTO;
import com.content.domain.entity.Category;

public class CategoryMocks {

    public static Set<Category> categoriesMock(int size) {
        Set<Category> categories = new HashSet<>();
        for (int i = 0; i < size; i++) {
            categories.add(categoryMock());
        }
        return categories;
    }

    public static Category categoryMock() {
        return Category.create("name", Set.of(1L, 2L, 3L));
    }

    public static Category categoryMock(Set<Long> vidsIds) {
        Set<Long> vids = new HashSet<>();
        vidsIds.forEach(id -> vids.add(id));
        return Category.create("name", vids);
    }

    public static AddCategoryDTO addCategoryDTOMock() {
        Set<Long> vids = new HashSet<>();
        vids.add(1L);
        vids.add(2L);
        vids.add(3L);
        return new AddCategoryDTO("name", vids);
    }

    public static AddCategoryDTO addCategoryDTOMock(Set<Long> vidsIds) {
        Set<Long> vids = new HashSet<>();
        vidsIds.forEach(id -> vids.add(id));
        return new AddCategoryDTO("name", vids);
    }
}
