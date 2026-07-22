package ru.kashtanov.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kashtanov.user_service.dto.request.RoleCreatedDto;
import ru.kashtanov.user_service.dto.request.RoleDto;
import ru.kashtanov.user_service.service.impl.RoleServiceImpl;

/**
 * @author Viktor Кashtanov
 */
@RestController
@RequestMapping("api/roles")
public class RoleController {
    private final RoleServiceImpl roleService;

    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleCreatedDto> createUser(@RequestBody RoleCreatedDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(dto.getRoleName()));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<RoleDto> deleteById(@PathVariable Long roleId) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.deleteRoleyId(roleId));
    }
}
