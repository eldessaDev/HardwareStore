package com.hardware.store.service;

import com.hardware.store.dto.UserDto;
import com.hardware.store.dto.UserRegisterDto;
import com.hardware.store.entity.Role;
import com.hardware.store.entity.User;
import com.hardware.store.exception.ResourceNotFoundException;
import com.hardware.store.mapper.UserMapper;
import com.hardware.store.repository.RoleRepository;
import com.hardware.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; // <--- 1. IMPORTANTE
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <--- 2. BUENA PRÁCTICA

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    // 3. INYECCIÓN DEL ENCRIPTADOR
    // Si esto te marca error rojo, es que te falta la dependencia de Security en el pom.xml
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true) // Optimiza la lectura
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for(User user : users){
            userDtos.add(userMapper.userToDto(user));
        }
        return userDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
        return userMapper.userToDto(user);
    }

    @Override
    @Transactional // Si falla algo, hace rollback
    public UserDto createUser(UserRegisterDto userRegisterDto) {
        // 1. Convertir
        User user = userMapper.toUserEntity(userRegisterDto);

        // 2. ENCRIPTAR LA CONTRASEÑA (OBLIGATORIO) 🛡️
        // Nunca guardes la password limpia.
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

        // 3. Buscar Rol
        Role role = roleRepository.findById(userRegisterDto.getRoleId())
                .orElseThrow(()-> new ResourceNotFoundException("Role not found"));

        // 4. Asignar y Guardar
        user.setRole(role);
        User savedUser = userRepository.save(user);

        return userMapper.userToDto(savedUser);
    }

    @Override
    @Transactional
    public UserDto updateUser(Integer id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        // ¡BIEN HECHO! Usar el ID del DTO es lo correcto.
        Role role = roleRepository.findById(userDto.getRoleId()) // <--- Esto asume que agregaste getRoleId() en UserDto
                .orElseThrow(()-> new ResourceNotFoundException("Role not found"));

        existingUser.setUsername(userDto.getUsername());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setRole(role);

        // Nota: No actualizamos la contraseña aquí por seguridad.

        User savedUser = userRepository.save(existingUser);
        return userMapper.userToDto(savedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }
}