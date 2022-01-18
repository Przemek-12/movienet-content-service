package com.content.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    void shouldAddVideoId() {
        Category category = Category.create("name", Set.of());
        category.addVideos(Set.of(1L, 2L));
        assertThat(category.getVideosIds().containsAll(Set.of(1L, 2L))).isTrue();
    }

    @Test
    void shouldRemoveVideoId() {
        Category category = Category.create("name", Set.of());
        category.addVideos(Set.of(1L, 2L));
        category.removeVideos(Set.of(1L, 2L));
        assertThat(category.getVideosIds().isEmpty()).isTrue();
    }
}
