package com.stefanini.librarybackend.dao.impl;

import com.stefanini.librarybackend.dao.AuthorDAO;
import com.stefanini.librarybackend.domain.Author;

public class AuthorDAOImpl extends DAOAbstractImpl<Author> implements AuthorDAO<Author> {
    public AuthorDAOImpl() {
        setClazz(Author.class);
    }
}
