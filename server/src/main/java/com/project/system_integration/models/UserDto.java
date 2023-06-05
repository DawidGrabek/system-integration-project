package com.project.system_integration.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String login;
    private String role;

    public UserDto(String login, String role) {
        this.login = login;
        this.role = role;
    }
}
