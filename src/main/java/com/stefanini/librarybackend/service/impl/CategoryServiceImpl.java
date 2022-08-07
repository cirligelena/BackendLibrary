package com.stefanini.librarybackend.service.impl;

import com.stefanini.librarybackend.dao.BookDAO;
import com.stefanini.librarybackend.dao.CategoryDAO;
import com.stefanini.librarybackend.dao.impl.BookDAOImpl;
import com.stefanini.librarybackend.dao.impl.CategoryDAOImpl;
import com.stefanini.librarybackend.domain.Book;
import com.stefanini.librarybackend.domain.Category;
import com.stefanini.librarybackend.service.CategoryService;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO<Category> categoryDAO;
    private final BookDAO<Book> bookDAO;

    public CategoryServiceImpl(CategoryDAOImpl categoryDAOImpl, BookDAOImpl bookDAOImpl) {
        this.categoryDAO = categoryDAOImpl;
        this.bookDAO = bookDAOImpl;
    }

    @Override
    public Category addCategory(Category category) {
        return categoryDAO.create(category);
    }

    @Override
    public int deleteCategory(int id) {
        return categoryDAO.removeById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDAO.getAll();
    }

    @Override
    public Category addBookToCategory(int bookId, int id) {
        Category category = categoryDAO.getById(id);
        Book book = bookDAO.getById(bookId);
        category.addBook(book);
        return categoryDAO.update(category);
    }
}
