package com.hardware.store.service;

import com.hardware.store.dto.RoleDto;
import com.hardware.store.entity.Role;
import com.hardware.store.mapper.RoleMapper;
import com.hardware.store.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements  RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper =  new RoleMapper();


    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roleList = roleRepository.findAll();
        List<RoleDto> roleDtoList = new ArrayList<>();
        for (Role role : roleList) {
            roleDtoList.add(roleMapper.roleToDto(role));
        }
        return roleDtoList;
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role role = roleMapper.roleToEntity(roleDto);
        Role savedRole = roleRepository.save(role);
        return roleMapper.roleToDto(savedRole);
    }
}
