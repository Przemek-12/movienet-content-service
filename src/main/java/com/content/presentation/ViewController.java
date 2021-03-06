package com.content.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.content.application.ViewService;
import com.content.application.dto.AddCategoryRequest;
import com.content.application.dto.AddViewRequest;
import com.content.application.dto.ViewDTO;
import com.content.application.exceptions.EntityObjectAlreadyExistsException;
import com.content.application.exceptions.EntityObjectNotFoundException;
import com.content.infrastructure.security.SecurityUtils;

@RestController
@RequestMapping("/view")
public class ViewController {

    private final ViewService viewService;

    @Autowired
    public ViewController(ViewService viewService) {
        this.viewService = viewService;
    }

    @PostMapping
    @Secured(SecurityUtils.ROLE_ADMIN)
    public ViewDTO addView(@RequestBody AddViewRequest addViewRequest) {
        try {
            return viewService.addView(addViewRequest);
        } catch (EntityObjectAlreadyExistsException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage(), e);
        }
    }

    @GetMapping
    public ViewDTO getViewByName(@RequestParam String name) {
        try {
            return viewService.getViewByName(name);
        } catch (EntityObjectNotFoundException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage(), e);
        }
    }

    @PutMapping("/category/add")
    @Secured(SecurityUtils.ROLE_ADMIN)
    public ViewDTO addCategory(@RequestBody AddCategoryRequest addCategoryRequest) {
        try {
            return viewService.addCategory(addCategoryRequest);
        } catch (EntityObjectNotFoundException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage(), e);
        }
    }

    @PutMapping("/category/remove")
    @Secured(SecurityUtils.ROLE_ADMIN)
    public ViewDTO removeCategory(@RequestParam String viewId, @RequestParam String categoryId) {
        try {
            return viewService.removeCategory(viewId, categoryId);
        } catch (EntityObjectNotFoundException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage(), e);
        }
    }

}
