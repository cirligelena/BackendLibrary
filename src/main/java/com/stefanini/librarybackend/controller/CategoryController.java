package com.stefanini.librarybackend.controller;

import com.stefanini.librarybackend.domain.Category;
import com.stefanini.librarybackend.service.CategoryService;
import com.stefanini.librarybackend.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/addCategory")
    public void addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
    }

    @DeleteMapping("/deleteCategory/{id}")
    public int deleteCategory(@PathVariable int id) {
        return categoryService.deleteCategory(id);
    }

    @GetMapping("/getAllCategories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
