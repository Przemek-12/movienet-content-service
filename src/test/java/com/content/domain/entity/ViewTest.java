package com.content.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class ViewTest {

    @Test
    void shouldAddCategory() {
        View view = View.create("name", Set.of());
        Category category = Category.create("name", Set.of());
        view.addCategory(category);
        assertThat(view.getCategories().contains(category)).isTrue();
    }

    @Test
    void shouldRemoveCategory() {
        View view = View.create("name", Set.of());
        Category category = Category.create("name", Set.of());
        view.addCategory(category);
        view.removeCategory(category);
        assertThat(view.getCategories().contains(category)).isFalse();
    }

}
