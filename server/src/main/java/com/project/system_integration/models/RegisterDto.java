package com.project.system_integration.models;

import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    private String login;
    private String password;
    private String role;
}
