package ru.kashtanov.user_service.dto.response.role;

import lombok.Data;

import java.util.Objects;

/**
 * @author Viktor Кashtanov
 */
@Data
public class RoleDeletedResponse {
    private Long id;
    private String roleName;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RoleDeletedResponse that = (RoleDeletedResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(roleName, that.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName);
    }
}
