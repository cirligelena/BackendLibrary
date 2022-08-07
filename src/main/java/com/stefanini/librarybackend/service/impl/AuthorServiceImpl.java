package com.stefanini.librarybackend.service.impl;

import com.stefanini.librarybackend.dao.AuthorDAO;
import com.stefanini.librarybackend.dao.BookDAO;
import com.stefanini.librarybackend.dao.impl.AuthorDAOImpl;
import com.stefanini.librarybackend.dao.impl.BookDAOImpl;
import com.stefanini.librarybackend.domain.Author;
import com.stefanini.librarybackend.domain.Book;
import com.stefanini.librarybackend.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDAO<Author> authorDAO;
    private final BookDAO<Book> bookDAO;

    public AuthorServiceImpl(AuthorDAOImpl authorDAOImpl, BookDAOImpl bookDAOImpl) {
        this.authorDAO = authorDAOImpl;
        this.bookDAO = bookDAOImpl;
    }

    @Override
    public Author addAuthor(Author author) {
        return authorDAO.create(author);
    }

    @Override
    public Author update(int id, Author author) {
        Author updatedAuthor = authorDAO.getById(id);
        setUpdatedAuthorData(updatedAuthor, author);
        return authorDAO.update(updatedAuthor);
    }

    @Override
    public int deleteAuthor(int id) {
        return authorDAO.removeById(id);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDAO.getAll();
    }

    @Override
    public Author findById(int id) {
        return authorDAO.getById(id);
    }

    @Override
    public Author addBookToAuthor(int bookId, int id) {
        Book book = bookDAO.getById(bookId);
        Author author = authorDAO.getById(id);
        author.addBook(book);
        return authorDAO.update(author);
    }

    private void setUpdatedAuthorData(Author updatedAuthor, Author author) {
        updatedAuthor.setFirstName(author.getFirstName());
        updatedAuthor.setLastName(author.getLastName());
        updatedAuthor.setBirthDate(author.getBirthDate());
        updatedAuthor.setBiography(author.getBiography());
    }
}
