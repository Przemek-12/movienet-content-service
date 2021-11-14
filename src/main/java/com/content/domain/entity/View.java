package com.content.domain.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Document
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class View {

    @Id
    @Getter
    private String id;

    @NonNull
    @Indexed(unique = true)
    @Getter
    private String name;

    @DBRef
    @Getter
    private Set<Category> categories = new HashSet<>();

    private View(@NonNull String name, Set<Category> categories) {
        this.name = name;
        this.categories.addAll(categories);
    }

    public static View create(String name, Set<Category> categories) {
        return new View(name, categories);
    }

    public View addCategory(Category category) {
        this.categories.add(category);
        return this;
    }

}
