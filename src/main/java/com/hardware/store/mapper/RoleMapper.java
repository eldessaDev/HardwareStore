package com.hardware.store.mapper;

import com.hardware.store.dto.RoleDto;
import com.hardware.store.entity.Role;

public class RoleMapper {

    public RoleDto roleToDto(Role role){
        if (role == null){
            return null;
        }
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        return roleDto;
    }

    public Role roleToEntity(RoleDto roleDto){
        if (roleDto == null){
            return null;
        }
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        return role;
    }

}
