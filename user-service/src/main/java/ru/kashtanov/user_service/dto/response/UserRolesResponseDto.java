package ru.kashtanov.user_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author Viktor Кashtanov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRolesResponseDto {
    private Long userId;
    private Long roleId;
    private String userEmail;
    private String roleName;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserRolesResponseDto that = (UserRolesResponseDto) o;
        return Objects.equals(userId, that.userId) && Objects.equals(roleId, that.roleId) && Objects.equals(userEmail, that.userEmail) && Objects.equals(roleName, that.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId, userEmail, roleName);
    }


}
