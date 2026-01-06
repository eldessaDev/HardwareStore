package com.hardware.store.service;

import com.hardware.store.dto.RoleDto;
import com.hardware.store.dto.UserDto;
import com.hardware.store.dto.UserRegisterDto;
import com.hardware.store.entity.Role;
import com.hardware.store.entity.User;
import com.hardware.store.exception.ResourceNotFoundException;
import com.hardware.store.mapper.UserMapper;
import com.hardware.store.repository.RoleRepository;
import com.hardware.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    //1. inyectamos
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    //2. iniciamos una variable del mapper
    private final UserMapper userMapper = new UserMapper();



    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for(User user : users){
            UserDto userDto = userMapper.userToDto(user);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public UserDto getUserById(Integer id) {
        //1. encontrar los user existentes
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        //2. convertirlo y retornarlo
        return userMapper.userToDto(user);
    }

    @Override
    public UserDto createUser(UserRegisterDto userRegisterDto) {
        // 1. Convertir datos básicos (Mapper)
        User user = userMapper.toUserEntity(userRegisterDto);

        // 2. BUSCAR EL ROL (CORRECCIÓN IMPORTANTE)
        // No creamos un RoleDto nuevo. Usamos el ID que viene en el formulario de registro.
        // Asumimos que userRegisterDto tiene un campo 'roleId'.
       Role role = roleRepository.findById(userRegisterDto.getRoleId()).orElseThrow(()-> new ResourceNotFoundException("Role not found"));

        // 3. ASIGNAR
        user.setRole(role);
        // 4. GUARDAR Y RETORNAR
        User savedUser = userRepository.save(user);
        return userMapper.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(Integer id, UserDto userDto) {
        // 1. Buscar Usuario Viejo
        User existingUser  = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found"));

        // 2. Buscar Rol Nuevo (Si queremos permitir cambiar el rol al actualizar)
        Role role = roleRepository.findById(userDto.getRoleId()).orElseThrow(()-> new ResourceNotFoundException("Role not found"));// aqui no tenia el getRoleId, lo puse en el userDto, nop se si ya con eso estara bien y no dara error.


        // 3. Actualizar datos
        existingUser.setUsername(userDto.getUsername());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setRole(role);

        // 4. Guardar
        User savedUser = userRepository.save(existingUser);
        return userMapper.userToDto(savedUser);


    }

    @Override
    public void deleteUser(Integer id) {
        User user =  userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);

    }


}
