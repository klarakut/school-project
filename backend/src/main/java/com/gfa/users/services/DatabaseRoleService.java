package com.gfa.users.services;

import com.gfa.common.dtos.PermissionRequestDto;
import com.gfa.common.dtos.RoleCreateRequestDto;
import com.gfa.common.dtos.RolePatchRequestDto;
import com.gfa.common.dtos.RoleResponseDto;
import com.gfa.users.models.Permission;
import com.gfa.users.models.Role;
import com.gfa.users.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseRoleService implements RoleService{

    private final RoleRepository roleRepository;

    public DatabaseRoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
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

        Boolean roleExists = roleRepository.findById(id).isPresent();
        if(!roleExists){
            throw new IdNotFoundException();
        }
        Role role = roleRepository.findById(id).get();
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

        //TODO -> unauthorized user => 401
        // TODO -> insufficient rights to create roles => 403

        Role role = new Role(dto.role);
        roleRepository.save(role);
        if (!roleRepository.findById(role.getId()).isPresent()){
            throw new UnknownErrorException();
        }
        RoleResponseDto roleDto = new RoleResponseDto(role.getId(),role.getRole());
        return roleDto;
    }

    @Override
    public RoleResponseDto update(Long id, RolePatchRequestDto dto) {
        if (id < 0){
            throw new NegativeIdException();
        }

        Boolean roleExists = roleRepository.findById(id).isPresent();
        if(!roleExists){
            throw new IdNotFoundException();
        }

        if (dto.role.isEmpty() || dto.role == null){
            throw new InvalidInputException();
        }

        //TODO -> unauthorized user => 401
        // TODO -> insufficient rights to create roles => 403

        Role role = roleRepository.findById(id).get();
        role.setRole(dto.role);
        roleRepository.save(role);
        if(!role.getRole().equals(dto.role)){
            throw new UnknownErrorException();
        }
        RoleResponseDto roleDto = new RoleResponseDto(role.getId(), dto.role);
        return roleDto;
    }

    @Override
    public void destroy(Long id) {
        if (id <= 0){
            throw new NegativeIdException();
        }

        Boolean roleExists = roleRepository.findById(id).isPresent();
        if(!roleExists){
            throw new IdNotFoundException();
        }

        //TODO -> unauthorized user => 401
        // TODO -> insufficient rights to create roles => 403

        roleRepository.deleteById(id);
    }

    @Override
    public void storePermission(Long id, PermissionRequestDto permission) {
        if(permission.ability.isEmpty()){
            throw new InvalidInputException();
        }

        Boolean existingRole = roleRepository.findById(id).isPresent();
        if (!existingRole){
            throw new IdNotFoundException();
        }

        //TODO -> unauthorized user => 401
        // TODO -> insufficient rights to create roles => 403

        Permission p = permissionRepository.findByAbility(permission.ability).get();
        Role role = roleRepository.findById(id).get();
        role.addPermission(p);
        roleRepository.save(role);

        if (!role.can(p)){
            throw new UnknownErrorException();
        }
    }

    @Override
    public void destroyPermission(Long id, Long permissionId) {
        Boolean existingRole = roleRepository.findById(id).isPresent();
        if(!existingRole){
            throw new IdNotFoundException();
        }
        Boolean existingPermission = permissionRepository.findById(permissionId).isPresent();
        if(!existingPermission){
            throw new PermissionIdNotFoundException();
        }

        //TODO -> unauthorized user => 401
        // TODO -> insufficient rights to create roles => 403

        Role role = roleRepository.findById(id).get();
        Permission permission = permissionRepository.findById(permissionId).get();
        Boolean hasPermission = role.can(permission);
        if(!hasPermission){
            throw new InvalidInputException();
        }
        role.removePermission(permission);
        roleRepository.save(role);

        if (role.can(permission)){
            throw new UnknownErrorException();
        }
    }
}
