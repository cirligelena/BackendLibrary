package com.stefanini.librarybackend.service.impl;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stefanini.librarybackend.dao.UserDAO;
import com.stefanini.librarybackend.dao.impl.UserDAOImpl;
import com.stefanini.librarybackend.domain.User;
import com.stefanini.librarybackend.domain.enums.Role;
import com.stefanini.librarybackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserDAO<User> userDao;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAOImpl userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        if(user.getRoles() == null){
            user.setRoles(new HashSet<>(Arrays.asList(Role.USER)));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.create(user);
    }

    @Override
    public User updateUser(int id, User user) {
        User u = findById(id);
        u.setEmail(user.getEmail());
        u.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.update(u);
    }

    @Override
    public List<User> showAllUsers() {
        return userDao.getAll();
    }

    @Override
    public User findById(int id) {
        return userDao.getById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public int deleteByEmail(String email) {
        return userDao.removeById(findByEmail(email).getId());
    }

    @Override
    public int deleteById(int id) {
        return userDao.removeById(id);
    }

    @Override
    @Transactional
    public User assignRole(int id, Role role) {
        User user = findById(id);
        user.getRoles().add(role);
        return userDao.update(user);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                // Removing useless chars from string to get the only token
                String refresh_token = authorizationHeader.substring("Bearer ".length());

                // Algorithm must be the same with creation token algorithm
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);

                // Taking email and roles from decoded token
                String email = decodedJWT.getSubject();
                User user = findByEmail(email);

                String access_token = JWT.create()
                        .withSubject(user.getEmail()) // TODO: change from userName to email after creating AppUserDetails
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::name).collect(Collectors.toList()))
                        .sign(algorithm);

                // Output access_token and refresh_token in header
                response.setHeader("access_token", access_token);
                response.setHeader("refresh_token", refresh_token);

                // Output access_token and refresh_token in body (JSON)
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                log.error("Error logging in: {}", exception.getMessage());
                response.setHeader("Error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                try {
                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", exception.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

}
