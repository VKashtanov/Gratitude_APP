package ru.kashtanov.user_service.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.kashtanov.user_service.dto.response.RoleResponseDto;
import ru.kashtanov.user_service.exception.role_exceptions.RoleCrudOperationsException;
import ru.kashtanov.user_service.model.Role;
import ru.kashtanov.user_service.repository.RoleRepo;
import ru.kashtanov.user_service.util.RoleUtilService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Viktor Кashtanov
 */
@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepo roleRepo;

    // Variables
    private Role role;
    private Role createdRole;

    @BeforeEach
    public void setUp() {
        role = new Role();
        role.setRoleId(1L);
        role.setRoleName("USER");

        createdRole = new Role();
        createdRole.setRoleId(1L);
        createdRole.setRoleName("USER_EXTENDED");

    }

    @Test
    public void testFindRoleById_Sc_OK() {
        // GIVEN
        Long roleId = 1L;
        when(roleRepo.findById(roleId)).thenReturn(Optional.of(role));

        //WHEN
        Role role1 = roleService.findRoleById(roleId);
        //THEN
        assertNotNull(role1);
        assertEquals(role.getRoleId(), role1.getRoleId());
        verify(roleRepo, times(1)).findById(roleId);
    }

    @Test
    public void testFindRoleById_Sc_NOT_FOUND() {
        // GIVEN
        Long roleId = 999L;
        when(roleRepo.findById(roleId)).thenReturn(Optional.empty());

        //WHEN THEN
        assertThrows(RoleCrudOperationsException.class, () -> roleService.findRoleById(roleId));

    }

    @Test
    public void createRole_SC_WHEN_IT_DOESNT_EXSIST_IN_DB() {
        // GIVEN
        String roleName = "USER_EXTENDED";
        when(roleRepo.findByRoleName(roleName)).thenReturn(Optional.empty());
        when(roleRepo.save(any(Role.class))).thenReturn(createdRole);

        // WHEN
        RoleResponseDto createdRoleDto = roleService.createRole(roleName);

        //THEN
        assertNotNull(createdRoleDto);
        assertEquals(roleName, createdRoleDto.getRoleName());
    }

    @Test
    public void createRole_SC_WHEN_IT_EXSIST_IN_DB() {
        // GIVEN
        String roleName = "USER";
        when(roleRepo.findByRoleName(roleName)).thenReturn(Optional.of(role));
        // WHEN THEN
        assertThrows(RoleCrudOperationsException.class, () -> roleService.createRole(roleName));
        verify(roleRepo, times(1)).findByRoleName(roleName);

    }
}