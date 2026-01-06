package com.hardware.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {


    private String username;
    private String email;
    private String password;
    private Integer roleId;

}
