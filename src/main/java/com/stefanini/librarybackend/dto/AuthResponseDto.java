package com.stefanini.librarybackend.dto;

import com.stefanini.librarybackend.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Set;

@Getter
public class AuthResponseDto {
    private int id;
    private String email;
    private String access_token;
    private String refresh_token;
    private Set<Role> roles;
    private String status;


    public AuthResponseDto(int id, String email, String access_token, String refresh_token, Set<Role> roles, String status) {
        this.id = id;
        this.email = email;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.roles = roles;
        this.status = status;
    }

    public AuthResponseDto(String email, String token) {
        this.email = email;
        this.access_token = token;
    }
}
