package com.content.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.content.application.dto.AddCategoryRequest;
import com.content.application.dto.AddViewRequest;
import com.content.application.dto.ViewDTO;
import com.content.application.exceptions.EntityObjectAlreadyExistsException;
import com.content.application.exceptions.EntityObjectNotFoundException;
import com.content.domain.entity.View;
import com.content.domain.repository.ViewRepository;

@Service
public class ViewService {

    private final ViewRepository viewRepository;
    private final CategoryService categoryService;

    @Autowired
    public ViewService(ViewRepository viewRepository, CategoryService categoryService) {
        this.viewRepository = viewRepository;
        this.categoryService = categoryService;
    }

    public ViewDTO addView(AddViewRequest addViewRequest) throws EntityObjectAlreadyExistsException {
        checkIfViewAlreadyExists(addViewRequest);
        View view = View.create(
                addViewRequest.getName(),
                categoryService.addCategories(addViewRequest.getCategories()));
        return mapToDTO(viewRepository.save(view));
    }

    public ViewDTO getViewByName(String name) throws EntityObjectNotFoundException {
        return mapToDTO(viewRepository.findByName(name)
                .orElseThrow(() -> new EntityObjectNotFoundException(View.class.getSimpleName())));
    }

    public ViewDTO addCategory(AddCategoryRequest addCategoryRequest) throws EntityObjectNotFoundException {
        View view = findViewEntityById(addCategoryRequest.getViewId());
        view.addCategory(categoryService.addCategory(addCategoryRequest.getAddCategoryDTO()));
        return mapToDTO(viewRepository.save(view));
    }

    private void checkIfViewAlreadyExists(AddViewRequest addViewRequest) throws EntityObjectAlreadyExistsException {
        if (viewRepository.existsByName(addViewRequest.getName())) {
            throw new EntityObjectAlreadyExistsException(View.class.getSimpleName());
        }
    }

    private View findViewEntityById(String viewId) throws EntityObjectNotFoundException {
        return viewRepository.findById(viewId)
                .orElseThrow(() -> new EntityObjectNotFoundException(View.class.getSimpleName()));
    }

    private ViewDTO mapToDTO(View view) {
        return ViewMapper.mapToDTO(view);
    }
}
