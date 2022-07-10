package com.gfa.users.services;

import com.gfa.common.dtos.RoleCreateRequestDto;
import com.gfa.common.dtos.RolePatchRequestDto;
import com.gfa.common.dtos.RoleResponseDto;
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
            throw new RoleMissingException();
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
            throw new InvalidBodyException();
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
        if (id < 0){
            throw new NegativeIdException();
        }

        Boolean roleExists = roleRepository.findById(id).isPresent();
        if(!roleExists){
            throw new IdNotFoundException();
        }

        return roleRepository.deleteById(id);
    }
}
