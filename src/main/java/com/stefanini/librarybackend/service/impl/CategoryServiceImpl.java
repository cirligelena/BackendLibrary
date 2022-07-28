package com.stefanini.librarybackend.service.impl;

import com.stefanini.librarybackend.dao.CategoryDAO;
import com.stefanini.librarybackend.dao.impl.CategoryDAOImpl;
import com.stefanini.librarybackend.domain.Category;
import com.stefanini.librarybackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDAO<Category> categoryDAO;

    public CategoryServiceImpl(CategoryDAOImpl categoryDAOImpl) {
        this.categoryDAO = categoryDAOImpl;
    }

    @Override
    public void addCategory(Category category) {
        categoryDAO.create(category);
    }

    @Override
    public void deleteCategory(int id) {
        categoryDAO.remove(id);
    }
}
