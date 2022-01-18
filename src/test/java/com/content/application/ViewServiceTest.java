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
import com.content.application.exceptions.EntityObjectAlreadyExistsException;
import com.content.application.exceptions.EntityObjectNotFoundException;
import com.content.domain.entity.Category;
import com.content.domain.entity.View;
import com.content.domain.repository.ViewRepository;

@ExtendWith(MockitoExtension.class)
public class ViewServiceTest {
    
    @InjectMocks
    private ViewService viewService;

    @Mock
    private ViewRepository viewRepository;
    
    @Mock
    private CategoryService categoryService;

    private void whenViewRepositorySaveThenAnswer() {
        when(viewRepository.save(Mockito.any(View.class)))
                .thenAnswer(i -> i.getArgument(0));
    }

    private void whenViewRepositoryFindByNameThenReturn(Optional<View> view) {
        when(viewRepository.findByName(Mockito.anyString()))
                .thenReturn(view);
    }

    private void whenViewRepositoryFindByIdThenReturn(Optional<View> view) {
        when(viewRepository.findById(Mockito.anyString()))
                .thenReturn(view);
    }

    private void viewExistsByName(boolean bool) {
        when(viewRepository.existsByName(Mockito.anyString()))
                .thenReturn(bool);
    }

    private void mockCategoryServiceAddCategories() {
        when(categoryService.addCategories(Mockito.anySet()))
                .thenReturn(Set.of());
    }

    private void mockCategoryServiceAddCategory(Category category) {
        when(categoryService.addCategory(Mockito.any(AddCategoryDTO.class)))
                .thenReturn(category);
    }

    private void mockCategoryServiceFindCategoryEntityById(Category category)
            throws EntityObjectNotFoundException {
        when(categoryService.findCategoryEntityById(Mockito.anyString()))
                .thenReturn(category);
    }

    @Test
    void shouldAddViewIfNotExistsByName() throws EntityObjectAlreadyExistsException {
        whenViewRepositorySaveThenAnswer();
        viewExistsByName(false);
        mockCategoryServiceAddCategories();

        viewService.addView(ViewMocks.addViewRequestMock());

        verify(viewRepository, times(1)).save(Mockito.any(View.class));
    }

    @Test
    void shouldNotAddViewIfAlreadyExistsByName() {
        viewExistsByName(true);

        assertThrows(EntityObjectAlreadyExistsException.class,
                () -> viewService.addView(ViewMocks.addViewRequestMock()),
                "View already exists.");

        verify(viewRepository, never()).save(Mockito.any(View.class));
    }

    @Test
    void shouldGetViewByName() throws EntityObjectNotFoundException {
        whenViewRepositoryFindByNameThenReturn(Optional.of(ViewMocks.viewMock()));

        assertThat(viewService.getViewByName("asd")).isNotNull();
    }

    @Test
    void shouldThrowExceptionIfViewNotFoundByName() throws EntityObjectNotFoundException {
        whenViewRepositoryFindByNameThenReturn(Optional.empty());

        assertThrows(EntityObjectNotFoundException.class,
                () -> viewService.getViewByName("asd"),
                "View not found.");
    }

    @Test
    void shouldAddCategory() throws EntityObjectNotFoundException {
        Category category = CategoryMocks.categoryMock();

        whenViewRepositorySaveThenAnswer();
        mockCategoryServiceAddCategory(category);
        whenViewRepositoryFindByIdThenReturn(Optional.of(ViewMocks.viewMock()));

        viewService.addCategory(ViewMocks.addCategoryRequestMock());

        verify(viewRepository, times(1))
                .save(Mockito.argThat(arg -> arg.getCategories().contains(category)));
    }

    @Test
    void shouldNotAddCategoryIfViewNotExists() {
        whenViewRepositoryFindByIdThenReturn(Optional.empty());

        assertThrows(EntityObjectNotFoundException.class,
                () -> viewService.addCategory(ViewMocks.addCategoryRequestMock()),
                "View not found.");

        verify(viewRepository, never()).save(Mockito.any(View.class));
    }

    @Test
    void shouldRemoveCategory() throws EntityObjectNotFoundException {
        Category category = CategoryMocks.categoryMock();
        Set<Category> categories = new HashSet<>();
        categories.add(category);
        View view = View.create("name", categories);

        whenViewRepositorySaveThenAnswer();
        mockCategoryServiceFindCategoryEntityById(category);
        whenViewRepositoryFindByIdThenReturn(Optional.of(view));

        viewService.removeCategory("id", "id");
    }

}
