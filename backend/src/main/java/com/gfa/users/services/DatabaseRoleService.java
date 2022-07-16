package com.gfa.users.services;

import com.gfa.common.dtos.*;
import com.gfa.users.Exception.*;
import com.gfa.users.models.Permission;
import com.gfa.users.models.Role;
import com.gfa.users.repositories.PermissionRepository;
import com.gfa.users.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseRoleService implements RoleService{

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public DatabaseRoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<RoleResponseDto> index() {
        List<Role> roles = roleRepository.findAll();
        List<RoleResponseDto> rolesDto = new ArrayList<>();
        for (Role role : roles){
            RoleResponseDto dto = new RoleResponseDto(role.getId(),role.getRole());
            rolesDto.add(dto);
        }
        return rolesDto;
    }

    @Override
    public RoleResponseDto show(Long id) {
        if (id < 0){
            throw new NegativeIdException();
        }

        Role role = roleRepository.findById(id).orElseThrow( () -> new IdNotFoundException());
        RoleResponseDto roleDto = new RoleResponseDto(role.getId(),role.getRole());
        return roleDto;
    }

    @Override
    public RoleResponseDto store(RoleCreateRequestDto dto) {
        if(dto.role.isEmpty() || dto.role == null){
            throw new EmptyBodyException();
        }

        Boolean existingRole = roleRepository.findByRole(dto.role).isPresent();
            if (existingRole){
            throw new RoleExistException();
        }

        // TODO -> unauthorized user => 401
        // TODO -> insufficient rights to create roles => 403

        try {
            Role role = new Role(dto.role);
            roleRepository.save(role);
            RoleResponseDto roleDto = new RoleResponseDto(role.getId(),role.getRole());
            return roleDto;
        }
        catch (Exception e){
            throw new UnknownErrorException();
        }
    }

    @Override
    public RoleResponseDto update(Long id, RolePatchRequestDto dto) {
        if (id < 0){
            throw new NegativeIdException();
        }

        if (dto.role.isEmpty() || dto.role == null){
            throw new InvalidInputException();
        }

        // TODO -> unauthorized user => 401
        // TODO -> insufficient rights to create roles => 403

        Role role = roleRepository.findById(id).orElseThrow(() -> new IdNotFoundException());

        try {
            role.setRole(dto.role);
            roleRepository.save(role);
            RoleResponseDto roleDto = new RoleResponseDto(role.getId(), dto.role);
            return roleDto;
        }
        catch (Exception e){
            throw new UnknownErrorException();
        }
    }

    @Override
    public StatusResponseDto destroy(Long id) {
        if (id <= 0){
            throw new NegativeIdException();
        }

        // TODO -> unauthorized user => 401
        // TODO -> insufficient rights to create roles => 403

        Role role = roleRepository.findById(id).orElseThrow(() -> new IdNotFoundException());
        roleRepository.deleteById(role.getId());
        StatusResponseDto status = new StatusResponseDto("ok");
        return status;
    }

    @Override
    public StatusResponseDto storePermission(Long id, PermissionRequestDto permissionRequestDto) {
        if(permissionRequestDto.ability.isEmpty()){
            throw new InvalidInputException();
        }

        // TODO -> unauthorized user => 401
        // TODO -> insufficient rights to create roles => 403

        Permission permission = permissionRepository.findById(permissionRequestDto.id).orElseThrow(() -> new PermissionIdNotFoundException());
        Role role = roleRepository.findById(id).orElseThrow(() -> new IdNotFoundException());

        Boolean hasPermission = role.can(permission);
        if(hasPermission){
            throw new InvalidInputException();
        }

        try {
                role.addPermission(permission);
                roleRepository.save(role);
                StatusResponseDto status = new StatusResponseDto("ok");
                return status;
        } catch (Exception e){
                throw new UnknownErrorException();
        }
    }

    @Override
    public StatusResponseDto destroyPermission(Long id, Long permissionId) {

        // TODO -> unauthorized user => 401
        // TODO -> insufficient rights to create roles => 403

        Role role = roleRepository.findById(id).orElseThrow(() -> new IdNotFoundException());
        Permission permission = permissionRepository.findById(permissionId).orElseThrow(() -> new PermissionIdNotFoundException());

        Boolean hasPermission = role.can(permission);
        if(!hasPermission){
            throw new InvalidInputException();
        }

        try {
            role.removePermission(permission);
            roleRepository.save(role);
            StatusResponseDto status = new StatusResponseDto("ok");
            return status;
        }
        catch (Exception e){
            throw new UnknownErrorException();
        }
    }
}
