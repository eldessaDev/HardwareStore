package com.hardware.store.service;

import com.hardware.store.dto.UserDto;
import com.hardware.store.dto.UserRegisterDto;
import com.hardware.store.entity.User;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();
    UserDto getUserById(Integer id);
    UserDto createUser(UserRegisterDto userRegisterDto);
    UserDto updateUser(Integer id, UserDto userDto);
    void deleteUser(Integer id);

}
