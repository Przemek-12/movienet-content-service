package com.content.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.content.application.CategoryService;
import com.content.application.dto.CategoryDTO;
import com.content.application.dto.UpdateCategoryNameRequest;
import com.content.application.dto.UpdateCategoryVideosRequest;
import com.content.application.exceptions.EntityObjectNotFoundException;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PutMapping("/name")
    public CategoryDTO changeCategoryName(@RequestBody UpdateCategoryNameRequest request) {
        try {
            return categoryService.changeCategoryName(request);
        } catch (EntityObjectNotFoundException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage(), e);
        }
    }

    @PutMapping("/videos/add")
    public CategoryDTO addVideosToCategory(@RequestBody UpdateCategoryVideosRequest request) {
        try {
            return categoryService.addVideosToCategory(request);
        } catch (EntityObjectNotFoundException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage(), e);
        }
    }

    @PutMapping("/videos/remove")
    public CategoryDTO removeVideosFromCategory(@RequestBody UpdateCategoryVideosRequest request) {
        try {
            return categoryService.removeVideosFromCategory(request);
        } catch (EntityObjectNotFoundException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage(), e);
        }
    }

    @PutMapping("/videos/remove/all")
    public void deleteVideoIdFromAllCategories(@RequestParam Long videoId) {
        categoryService.deleteVideoIdFromAllCategories(videoId);
    }

    @DeleteMapping
    public void deleteCategoryById(@RequestParam String categoryId) {
        try {
            categoryService.deleteCategoryById(categoryId);
        } catch (EntityObjectNotFoundException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage(), e);
        }
    }

}
