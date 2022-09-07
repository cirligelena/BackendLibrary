package com.stefanini.librarybackend.service;

import com.stefanini.librarybackend.domain.Book;
import com.stefanini.librarybackend.domain.History;
import com.stefanini.librarybackend.domain.User;
import com.stefanini.librarybackend.domain.enums.Role;
import com.stefanini.librarybackend.dto.AuthResponseDto;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(int id, User user);
    List<User> showAllUsers();
    public User findById(int id);
    User findByEmail(String email);
    int deleteByEmail(String email);
    int deleteById(int id);

    User assignRole(int id, Role role);
    List<History> getUserHistory(int userId);
    List<Book> getUserBooks(int userId);
    User changePassword(int id, String password);
}
