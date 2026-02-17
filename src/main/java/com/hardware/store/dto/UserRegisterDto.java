package com.hardware.store.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {


    @NotBlank(message = "{user.username.mandatory}")
    private String username;

    @NotBlank(message = "{user.email.mandatory}")
    @Email(message = "{user.email.invalid}")
    private String email;

    @NotBlank(message = "{user.password.mandatory}")
    @Size(min = 6, message = "{user.password.size}")
    private String password;

    @NotNull(message = "{user.role.mandatory}")
    private Integer roleId;

}
