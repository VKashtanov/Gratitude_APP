package ru.kashtanov.user_service.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kashtanov.user_service.model.Role;
import ru.kashtanov.user_service.model.User;
import ru.kashtanov.user_service.repository.UsersRolesRepo;
import ru.kashtanov.user_service.service.UserService;

import static org.mockito.Mockito.when;

/**
 * @author Viktor Кashtanov
 */
@ExtendWith(MockitoExtension.class)
class UsersRolesServiceImplTest {
    @InjectMocks
    private UsersRolesServiceImpl usersRolesService;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private RoleServiceImpl roleService;
    @Mock
    private UsersRolesRepo usersRolesRepo;

    private User user;
    private Role role;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("username_1");
        user.setPassword("password_1");

        role = new Role();
        role.setRoleName("ADMIN");
        role.setRoleId(1L);
    }

    @Test
    void attachRoleOnUser_WhenValidData() {
        Long userId = 1L;
        Long roleId = 1L;
        when(userService.findUserById(userId)).thenReturn(user);
        when(roleService.findRoleById(roleId)).thenReturn(role);
        when(usersRolesRepo.findByRoleIdAndUserId(roleId,userId)).thenReturn(usersRolesRepo.findByRoleIdAndUserId(roleId,userId));
        usersRolesService.attachRoleOnUser(user.getId(), role.getRoleId());
    }

}