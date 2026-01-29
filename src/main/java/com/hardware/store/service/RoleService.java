package com.hardware.store.service;

import com.hardware.store.dto.RoleDto;
import java.util.List;

public interface RoleService {
    List<RoleDto> getAllRoles();
    RoleDto createRole(RoleDto roleDto);
}