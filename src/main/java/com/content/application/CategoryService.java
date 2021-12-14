package com.content.application;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.content.application.dto.AddCategoryDTO;
import com.content.application.dto.CategoryDTO;
import com.content.application.dto.UpdateCategoryNameRequest;
import com.content.application.dto.UpdateCategoryVideosRequest;
import com.content.application.exceptions.EntityObjectNotFoundException;
import com.content.domain.entity.Category;
import com.content.domain.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    protected Set<Category> addCategories(Set<AddCategoryDTO> categoriesRequests) {
        return categoriesRequests.stream()
                .map(categoryReq -> addCategory(categoryReq))
                .collect(Collectors.toSet());
    }

    protected Category addCategory(AddCategoryDTO addCategoryDTO) {
        return categoryRepository.save(CategoryMapper.mapToCategory(addCategoryDTO));
    }

    protected Category findCategoryEntityById(String id) throws EntityObjectNotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityObjectNotFoundException(Category.class.getSimpleName()));
    }

    public void deleteCategoryById(String id) throws EntityObjectNotFoundException {
        checkIfCategoryExists(id);
        categoryRepository.deleteById(id);
    }

    public void deleteVideoIdFromAllCategories(Long videoId) {
        Set<Category> categories = categoryRepository.findByVideosIds(videoId);
        categories.stream().forEach(category -> {
            category.getVideosIds().remove(videoId);
            categoryRepository.save(category);
        });
    }

    public CategoryDTO changeCategoryName(UpdateCategoryNameRequest request) throws EntityObjectNotFoundException {
        Category category = findCategoryEntityById(request.getCategoryId());
        category.changeName(request.getName());
        return mapToDTO(categoryRepository.save(category));
    }

    public CategoryDTO addVideosToCategory(UpdateCategoryVideosRequest request) throws EntityObjectNotFoundException {
        Category category = findCategoryEntityById(request.getCategoryId());
        category.addVideos(request.getVideosIds());
        return mapToDTO(categoryRepository.save(category));
    }

    public CategoryDTO removeVideosFromCategory(UpdateCategoryVideosRequest request)
            throws EntityObjectNotFoundException {
        Category category = findCategoryEntityById(request.getCategoryId());
        category.removeVideos(request.getVideosIds());
        return mapToDTO(categoryRepository.save(category));
    }

    private void checkIfCategoryExists(String categoryId) throws EntityObjectNotFoundException {
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityObjectNotFoundException(Category.class.getSimpleName());
        }
    }

    private CategoryDTO mapToDTO(Category category) {
        return CategoryMapper.mapToDTO(category);
    }

}
