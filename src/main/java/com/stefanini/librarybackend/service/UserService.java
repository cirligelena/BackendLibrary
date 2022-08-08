package com.stefanini.librarybackend.service;

import com.stefanini.librarybackend.domain.Profile;
import com.stefanini.librarybackend.domain.User;
import com.stefanini.librarybackend.domain.enums.Role;

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
    public User createUserProfile(User user , Profile profile);
}
