package ru.kashtanov.user_service.service.impl;

import org.springframework.stereotype.Service;
import ru.kashtanov.user_service.dto.request.role.UserRoleDto;
import ru.kashtanov.user_service.exception.user_roles_exceptions.UserRolesCrudException;
import ru.kashtanov.user_service.model.Role;
import ru.kashtanov.user_service.model.User;
import ru.kashtanov.user_service.model.join_tables.UsersRoles;
import ru.kashtanov.user_service.repository.UsersRolesRepo;

/**
 * @author Viktor Кashtanov
 */
@Service
public class UsersRolesServiceImpl {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;
    private final UsersRolesRepo usersRolesRepo;

    public UsersRolesServiceImpl(UserServiceImpl userService, RoleServiceImpl roleService, UsersRolesRepo usersRolesRepo) {
        this.userService = userService;
        this.roleService = roleService;
        this.usersRolesRepo = usersRolesRepo;
    }

    public UsersRoles attachRoleOnUser(Long userId, Long roleId) {
        var dto = validateAndGet(userId, roleId);
        var usersRoles = new UsersRoles(null, dto.role(), dto.user());
        return usersRolesRepo.save(usersRoles);
    }


    private UserRoleDto validateAndGet(Long userId, Long roleId) {
        if (userId == null) throw new UserRolesCrudException("userId is null");
        if (roleId == null) throw new UserRolesCrudException("roleId is null");

        User user = userService.findUserById(userId);
        Role role = roleService.findRoleById(roleId);

        usersRolesRepo.findByRoleIdAndUserId(roleId, userId).ifPresent(ur -> {
            throw new UserRolesCrudException("User with this role already exists");
        });
        return new UserRoleDto(user, role);

    }


}
