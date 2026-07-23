package ru.kashtanov.user_service.dto.response;

import lombok.Data;

import java.util.Objects;

/**
 * @author Viktor Кashtanov
 */
@Data
public class RoleResponseDto {
    private String roleName;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RoleResponseDto that = (RoleResponseDto) o;
        return Objects.equals(roleName, that.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(roleName);
    }
}
