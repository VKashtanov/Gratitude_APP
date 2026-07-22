package ru.kashtanov.user_service.dto.request;

import lombok.Data;

import java.util.Objects;

/**
 * @author Viktor Кashtanov
 */
@Data
public class RoleCreatedDto {
    private String roleName;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RoleCreatedDto that = (RoleCreatedDto) o;
        return Objects.equals(roleName, that.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(roleName);
    }
}
