package ru.kashtanov.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.user_service.dto.request.role.UserAttachRoleRequest;
import ru.kashtanov.user_service.dto.response.RoleResponseDto;
import ru.kashtanov.user_service.dto.request.role.RoleDto;
import ru.kashtanov.user_service.dto.response.UserRolesResponseDto;
import ru.kashtanov.user_service.model.join_tables.UsersRoles;
import ru.kashtanov.user_service.service.impl.RoleServiceImpl;
import ru.kashtanov.user_service.service.impl.UsersRolesServiceImpl;
import ru.kashtanov.user_service.util.UserRolesUtilService;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("api/roles")
public class RoleController {
    private final RoleServiceImpl roleService;

    private final UsersRolesServiceImpl usersRolesService;

    public RoleController(RoleServiceImpl roleService, UsersRolesServiceImpl usersRolesService) {
        this.roleService = roleService;
        this.usersRolesService = usersRolesService;
    }

    @PostMapping
    public ResponseEntity<RoleResponseDto> createUser(@RequestBody RoleResponseDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(dto.getRoleName()));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<RoleDto> deleteById(@PathVariable Long roleId) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.deleteRoleyId(roleId));
    }

    @PostMapping("/attach")
    public ResponseEntity<UserRolesResponseDto> attachRoleOnUser(@RequestBody UserAttachRoleRequest dto) {

        UsersRoles usersRoles = usersRolesService.attachRoleOnUser(dto.userId(), dto.roleId());
        var response = UserRolesUtilService.toUserRolesResponseDto(usersRoles);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
