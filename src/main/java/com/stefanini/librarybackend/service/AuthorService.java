package com.stefanini.librarybackend.service;

import com.stefanini.librarybackend.domain.Author;

import java.util.List;

/**
 * @author dcuciuc
 */
public interface AuthorService {

    Author addAuthor(Author author);

    Author update(int id, Author author);

    int deleteAuthor(int id);

    List<Author> getAllAuthors();

    Author findById(int id);
}
