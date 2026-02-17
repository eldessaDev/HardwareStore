package com.hardware.store.mapper;

import com.hardware.store.dto.UserDto;
import com.hardware.store.dto.UserRegisterDto;
import com.hardware.store.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto userToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());

        // CORRECCIÓN: Si UserDto tiene 'roleId', debemos sacar el ID del objeto Role.
        if (user.getRole() != null) {
            // Asumiendo que tu UserDto tiene un campo "roleId" (Integer)
            // Si tu UserDto tiene "Role role", avísame, pero lo ideal es pasar solo el ID o el Nombre.
            // userDto.setRoleId(user.getRole().getId()); <--- Lo ideal
            userDto.setRole(user.getRole()); // <--- Lo que tienes ahora (funciona si DTO tiene objeto Role)
        }

        return userDto;
    }

    public User userToEntity(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        // No seteamos el rol aquí, lo hace el servicio buscando en la BD por seguridad.
        return user;
    }

    public User toUserEntity(UserRegisterDto userRegisterDto){
        User user = new User();
        user.setUsername(userRegisterDto.getUsername());
        user.setEmail(userRegisterDto.getEmail());
        // La contraseña la pasamos tal cual, PERO el servicio la debe encriptar después.
        user.setPassword(userRegisterDto.getPassword());
        return user;
    }
}
