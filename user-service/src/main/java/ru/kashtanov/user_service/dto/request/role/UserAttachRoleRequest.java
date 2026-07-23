package ru.kashtanov.user_service.dto.request.role;

import lombok.Data;

/**
 * @author Viktor Кashtanov
 */

public record UserAttachRoleRequest(Long userId, Long roleId) {

}
