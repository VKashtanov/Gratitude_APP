package ru.kashtanov.user_service.service.impl;

import org.springframework.stereotype.Service;
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

    public void attachRoleOnUser(Long userId, Long roleId) {
        if (userId == null) throw new UserRolesCrudException("userId is null");
        if (roleId == null) throw new UserRolesCrudException("roleId is null");

        User userById = userService.findUserById(userId);
        Role roleById = roleService.findRoleById(roleId);

//        usersRolesRepo.findByUserIdR
        var usersRoles = new UsersRoles(null, roleById, userById);
        usersRolesRepo.save(usersRoles);
    }


}
