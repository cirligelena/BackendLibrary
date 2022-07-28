package com.stefanini.librarybackend.dao.impl;

import com.stefanini.librarybackend.dao.UserRoleDAO;
import com.stefanini.librarybackend.domain.UserRole;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
@Repository
public class UserRoleDAOImpl  extends DAOAbstractImpl<UserRole> implements UserRoleDAO {

    public UserRoleDAOImpl() {
        setClazz(UserRole.class);
    }

    @Override
    public UserRole findUserRoleByEmail(String email) {
        TypedQuery query = entityManager.createQuery("select a from UserRole a where a.email = ?1", UserRole.class);
        query.setParameter(1, email);
        return (UserRole) query.getSingleResult();
    }
}
