package ru.kashtanov.user_service.dto.request.role;

import ru.kashtanov.user_service.model.Role;
import ru.kashtanov.user_service.model.User;

/**
 * @author Viktor Кashtanov
 */
public record UserRoleDto(User user, Role role) {
}
