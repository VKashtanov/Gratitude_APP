package ru.kashtanov.user_service.util;

import ru.kashtanov.user_service.dto.request.RoleDto;
import ru.kashtanov.user_service.model.Role;

/**
 * @author Viktor Кashtanov
 */
public class RoleUtilService {

    public static RoleDto toRoleDto(Role role) throws IllegalArgumentException  {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        var dto = new RoleDto();
        dto.setRoleName(role.getRoleName());
        dto.setId(role.getRoleId());
        return dto;
    }
}
