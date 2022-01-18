package com.content.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.content.application.dto.AddCategoryDTO;
import com.content.application.dto.UpdateCategoryNameRequest;
import com.content.application.dto.UpdateCategoryVideosRequest;
import com.content.application.exceptions.EntityObjectNotFoundException;
import com.content.application.feign.VideoServiceFeign;
import com.content.domain.entity.Category;
import com.content.domain.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private VideoServiceFeign videoServiceFeign;

    private void whenCategoryRepositorySaveThenAnswer() {
        when(categoryRepository.save(Mockito.any(Category.class)))
                .thenAnswer(i -> i.getArgument(0));
    }

    private void whenCategoryRepositoryFindByIdTheReturn(Optional<Category> category) {
        when(categoryRepository.findById(Mockito.anyString()))
                .thenReturn(category);
    }

    private void whenCategoryRepositoryFindByVideosIdsTheReturnEntities(int size) {
        when(categoryRepository.findByVideosIds(Mockito.anyLong()))
                .thenReturn(CategoryMocks.categoriesMock(size));
    }

    private void videoExistsById(boolean bool) {
        when(videoServiceFeign.videoExistsById(Mockito.anyLong()))
                .thenReturn(bool);
    }

    private void categoryExists(boolean bool) {
        when(categoryRepository.existsById(Mockito.anyString()))
                .thenReturn(bool);
    }

    @Test
    void shouldAddCategories() {
        whenCategoryRepositorySaveThenAnswer();
        videoExistsById(true);

        Set<AddCategoryDTO> cats = new HashSet<>();
        cats.add(CategoryMocks.addCategoryDTOMock());
        cats.add(CategoryMocks.addCategoryDTOMock());

        Set<Category> categories = categoryService.addCategories(cats);

        verify(categoryRepository, times(2)).save(Mockito.any(Category.class));
        assertThat(categories).hasSize(2);
    }

    @Test
    void shouldFilterVideosThatNotExistWhenAddCategory() {
        whenCategoryRepositorySaveThenAnswer();
        videoExistsById(false);

        Category category = categoryService.addCategory(CategoryMocks.addCategoryDTOMock());

        verify(categoryRepository, times(1)).save(Mockito.any(Category.class));
        assertThat(category.getVideosIds()).isEmpty();
    }

    @Test
    void shouldFindCategoryEntityById() throws EntityObjectNotFoundException {
        whenCategoryRepositoryFindByIdTheReturn(Optional.of(CategoryMocks.categoryMock()));

        assertThat(categoryService.findCategoryEntityById("asd")).isNotNull();
    }

    @Test
    void shouldThrowExceptionIfCategoryEntityNotFound() {
        whenCategoryRepositoryFindByIdTheReturn(Optional.empty());

        assertThrows(EntityObjectNotFoundException.class,
                () -> categoryService.findCategoryEntityById("asd"),
                "Category not found.");
    }

    @Test
    void shouldDeleteCategoryByIdIfExists() throws EntityObjectNotFoundException {
        categoryExists(true);

        categoryService.deleteCategoryById("Asd");
        verify(categoryRepository, times(1)).deleteById(Mockito.anyString());
    }

    @Test
    void shouldNotDeleteCategoryByIdIfNotExists() throws EntityObjectNotFoundException {
        categoryExists(false);

        assertThrows(EntityObjectNotFoundException.class,
                () -> categoryService.deleteCategoryById("Asd"),
                "Category not found.");
        verify(categoryRepository, never()).deleteById(Mockito.anyString());
    }

    @Test
    void shouldDeleteVideoIdFromAllCategories() {
        whenCategoryRepositoryFindByVideosIdsTheReturnEntities(10);

        final long VIDEO_ID_TO_REMOVE = 1L;

        categoryService.deleteVideoIdFromAllCategories(VIDEO_ID_TO_REMOVE);

        verify(categoryRepository, times(10))
                .save(Mockito.argThat(arg -> !arg.getVideosIds()
                        .contains(VIDEO_ID_TO_REMOVE)));
    }

    @Test
    void shouldChangeCategoryName() throws EntityObjectNotFoundException {
        Category category = Category.create("name", Set.of(1L, 2L, 3L));

        whenCategoryRepositorySaveThenAnswer();
        whenCategoryRepositoryFindByIdTheReturn(Optional.of(category));

        final String NAME_TO_CHANGE = "Name123";

        categoryService.changeCategoryName(UpdateCategoryNameRequest.builder()
                .categoryId("id")
                .name(NAME_TO_CHANGE)
                .build());

        verify(categoryRepository, times(1))
                .save(Mockito.argThat(arg -> arg.getName().equals(NAME_TO_CHANGE)));
    }

    @Test
    void shouldAddVideosToCategory() throws EntityObjectNotFoundException {
        whenCategoryRepositorySaveThenAnswer();
        whenCategoryRepositoryFindByIdTheReturn(
                Optional.of(CategoryMocks.categoryMock(Set.of(1L, 2L, 3L))));
        videoExistsById(true);

        final Set<Long> VIDEOS_TO_ADD = new HashSet<>();
        VIDEOS_TO_ADD.add(11L);
        VIDEOS_TO_ADD.add(21L);
        VIDEOS_TO_ADD.add(31L);

        categoryService.addVideosToCategory(UpdateCategoryVideosRequest.builder()
                .categoryId("id")
                .videosIds(VIDEOS_TO_ADD)
                .build());

        verify(categoryRepository, times(1))
                .save(Mockito.argThat(arg -> arg.getVideosIds().containsAll(VIDEOS_TO_ADD)));

    }

    @Test
    void shouldRemoveVideosFromCategory() throws EntityObjectNotFoundException {
        final Set<Long> VIDEOS_TO_REMOVE = new HashSet<>();
        VIDEOS_TO_REMOVE.add(11L);
        VIDEOS_TO_REMOVE.add(21L);
        VIDEOS_TO_REMOVE.add(31L);

        whenCategoryRepositorySaveThenAnswer();
        whenCategoryRepositoryFindByIdTheReturn(
                Optional.of(CategoryMocks.categoryMock(VIDEOS_TO_REMOVE)));

        categoryService.removeVideosFromCategory(UpdateCategoryVideosRequest.builder()
                .categoryId("id")
                .videosIds(VIDEOS_TO_REMOVE)
                .build());
        
        verify(categoryRepository, times(1))
                .save(Mockito
                        .argThat(arg -> arg.getVideosIds().stream()
                                .noneMatch(id -> VIDEOS_TO_REMOVE.contains(id))));
    }

}
