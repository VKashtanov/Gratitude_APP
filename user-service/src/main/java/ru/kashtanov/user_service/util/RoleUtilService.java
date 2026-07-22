package ru.kashtanov.user_service.util;

import ru.kashtanov.user_service.dto.response.role.RoleDeletedResponse;
import ru.kashtanov.user_service.model.Role;

import java.util.Optional;

/**
 * @author Viktor Кashtanov
 */
public class RoleUtilService {

    public static RoleDeletedResponse toRoleDeletedResponse(Role role) throws IllegalArgumentException  {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        var response = new RoleDeletedResponse();
        response.setRoleName(role.getRoleName());
        response.setId(role.getRoleId());
        return response;
    }
}
