package ru.kashtanov.user_service.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kashtanov.user_service.dto.request.RoleCreatedDto;
import ru.kashtanov.user_service.dto.request.RoleDto;
import ru.kashtanov.user_service.exception.role_exceptions.RoleCrudOperationsException;
import ru.kashtanov.user_service.model.Role;
import ru.kashtanov.user_service.repository.RoleRepo;
import ru.kashtanov.user_service.util.RoleUtilService;

import java.util.Optional;

/**
 * @author Viktor Кashtanov
 */
@Service
@Slf4j
public class RoleServiceImpl {
    private final RoleRepo roleRepo;

    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public Role findRoleById(Long id) {
        Optional<Role> role = roleRepo.findById(id);
        if (role.isEmpty()) {
            throw new RoleCrudOperationsException("Role not found");
        }
        return role.get();
    }


    public RoleCreatedDto createRole(String roleName) {
        if (roleName == null || roleName.isBlank()) {
            throw new RoleCrudOperationsException("Role name cannot be empty");
        }
        Optional<Role> optionalRole = roleRepo.findByRoleName(roleName);
        if (optionalRole.isPresent()) {
            throw new RoleCrudOperationsException("Can't create a role with the name " + roleName + " already exists");
        }
        roleRepo.save(new Role(roleName));
        var createdDto = new RoleCreatedDto();
        createdDto.setRoleName(roleName);
        log.info("Role created: {}", createdDto);
        return createdDto;
    }

    public RoleDto deleteRoleyId(Long id) {
        if (id == null) {
            throw new RoleCrudOperationsException("Id cannot be null");
        }
        Optional<Role> optionalRole = roleRepo.findById(id);
        if (optionalRole.isEmpty()) {
            throw new RoleCrudOperationsException("Role with id " + id + " does not exist");
        }
        Role role = optionalRole.get();
        roleRepo.delete(role);
        return RoleUtilService.toRoleDto(role);

    }
}
