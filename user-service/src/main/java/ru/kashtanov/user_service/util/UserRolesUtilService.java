package ru.kashtanov.user_service.util;

import ru.kashtanov.user_service.dto.response.UserRolesResponseDto;
import ru.kashtanov.user_service.model.join_tables.UsersRoles;

/**
 * @author Viktor Кashtanov
 */
public class UserRolesUtilService {

    public static UserRolesResponseDto toUserRolesResponseDto(UsersRoles usersRoles) {
        if (usersRoles == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        if (usersRoles.getUser() == null) {
            throw new IllegalArgumentException("User in UsersRoles cannot be null");
        }
        if (usersRoles.getRole() == null) {
            throw new IllegalArgumentException("Role in UsersRoles cannot be null");
        }
        var dto = new UserRolesResponseDto();
        dto.setUserId(usersRoles.getUser().getId());
        dto.setRoleId(usersRoles.getRole().getRoleId());
        dto.setRoleName(usersRoles.getRole().getRoleName());
        dto.setUserEmail(usersRoles.getUser().getEmail());
        return dto;
    }
}
