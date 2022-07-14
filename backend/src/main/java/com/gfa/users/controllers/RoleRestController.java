package com.gfa.users.controllers;

import com.gfa.common.dtos.*;
import com.gfa.users.Exception.*;
import com.gfa.users.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
public class RoleRestController {

    private final RoleService roleService;

    @Autowired
    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    // #GET /roles
    @GetMapping("/roles")
    public List<RoleResponseDto> index(){
        return roleService.index();
    }

    // #POST /roles/
    @PostMapping("/roles")
    public ResponseEntity<? extends ResponseDto> store(@RequestBody RoleCreateRequestDto dto){
        try{
            RoleResponseDto role = roleService.store(dto);
            return new ResponseEntity<>(role,HttpStatus.CREATED);
        }
        catch (EmptyBodyException e){
            return new ResponseEntity<>(new ErrorResponseDto("Role is required"), HttpStatus.BAD_REQUEST);
        }
        catch (RoleExistException e){
            return new ResponseEntity<>(new ErrorResponseDto("Role already exists"),HttpStatus.CONFLICT);
        }
        catch (UnknownErrorException e){
            return new ResponseEntity<>(new ErrorResponseDto("Unknown error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //TODO
        /*catch (HttpClientErrorException.Unauthorized e){
            return new ResponseEntity<>(new ErrorResponseDto("Unauthorized user"), HttpStatus.UNAUTHORIZED);
        }
        catch (HttpClientErrorException.Forbidden e){
            return new ResponseEntity<>(new ErrorResponseDto("Insufficient rights to create roles"), HttpStatus.FORBIDDEN);
        }*/
    }

    // #GET /roles/{id}
    @GetMapping("/roles/{id}")
    public ResponseEntity<? extends ResponseDto> show(@PathVariable("id") Long id){
        try {
            RoleResponseDto responseDto = roleService.show(id);
            return new ResponseEntity<>(responseDto,HttpStatus.resolve(200));
        }
        catch (NegativeIdException e){
            return new ResponseEntity<>(new ErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
        }
        catch (IdNotFoundException e){
            return new ResponseEntity<>(new ErrorResponseDto("Role not found"),HttpStatus.NOT_FOUND);
        }
    }

    // #PUT
    @PutMapping("/roles/{id}")
    public ResponseEntity<? extends ResponseDto> update(@PathVariable("id") Long id, @RequestBody RolePatchRequestDto dto){
        try {
            RoleResponseDto responseDto = roleService.update(id,dto);
            return new ResponseEntity<>(responseDto,HttpStatus.resolve(200));
        }
        catch (NegativeIdException e){
            return new ResponseEntity<>(new ErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
        }
        catch (IdNotFoundException e){
            return new ResponseEntity<>(new ErrorResponseDto("Role not found"),HttpStatus.NOT_FOUND);
        }
        catch (InvalidInputException e){
            return new ResponseEntity<>(new ErrorResponseDto("Invalid data"),HttpStatus.BAD_REQUEST);
        }
        catch (UnknownErrorException e){
            return new ResponseEntity<>(new ErrorResponseDto("Unknown error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // TODO
        /*catch (HttpClientErrorException.Unauthorized e){
            return new ResponseEntity<>(new ErrorResponseDto("Unauthorized user"), HttpStatus.UNAUTHORIZED);
        }
        catch (HttpClientErrorException.Forbidden e){
            return new ResponseEntity<>(new ErrorResponseDto("Insufficient rights to create roles"), HttpStatus.FORBIDDEN);
        }*/
    }

    // #DELETE
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<? extends ResponseDto> destroy(@PathVariable("id") Long id){
        try {
            roleService.destroy(id);
            return new ResponseEntity<>(HttpStatus.resolve(201));
        }
        catch (NegativeIdException e){
            return new ResponseEntity<>(new ErrorResponseDto("Invalid id"), HttpStatus.BAD_REQUEST);
        }
        catch (IdNotFoundException e){
            return new ResponseEntity<>(new ErrorResponseDto("Role not found"),HttpStatus.NOT_FOUND);
        }
        // TODO
        /*catch (HttpClientErrorException.Unauthorized e){
            return new ResponseEntity<>(new ErrorResponseDto("Unauthorized user"), HttpStatus.UNAUTHORIZED);
        }
        catch (HttpClientErrorException.Forbidden e){
            return new ResponseEntity<>(new ErrorResponseDto("Insufficient rights to create roles"), HttpStatus.FORBIDDEN);
        }*/
    }

    // #POST
    @PostMapping("roles/{id}/permissions")
    public ResponseEntity<? extends ResponseDto> storePermission(@PathVariable ("id") Long id, @RequestBody PermissionRequestDto permission){
        try {
            roleService.storePermission(id,permission);
            return new ResponseEntity<>(HttpStatus.resolve(200));
        }
        catch (InvalidInputException e){
            return new ResponseEntity<>(new ErrorResponseDto("Invalid data"), HttpStatus.BAD_REQUEST);
        }
        catch (IdNotFoundException e){
            return new ResponseEntity<>(new ErrorResponseDto("Role not found"), HttpStatus.NOT_FOUND);
        }
        catch (UnknownErrorException e){
            return new ResponseEntity<>(new ErrorResponseDto("Unknown error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // TODO
        /*catch (HttpClientErrorException.Unauthorized e){
            return new ResponseEntity<>(new ErrorResponseDto("Unauthorized user"), HttpStatus.UNAUTHORIZED);
        }
        catch (HttpClientErrorException.Forbidden e){
            return new ResponseEntity<>(new ErrorResponseDto("Insufficient rights to create roles"), HttpStatus.FORBIDDEN);
        }*/
    }

    //#DELETE
    @DeleteMapping("roles/{id}/permissions/{permission_id}")
    public ResponseEntity<? extends ResponseDto> destroyPermission(@PathVariable ("id") Long id, @PathVariable ("permissionId") Long permissionId){
        try {
            roleService.destroyPermission(id,permissionId);
            return new ResponseEntity<>(HttpStatus.resolve(204));
        }
        catch (IdNotFoundException e){
            return new ResponseEntity<>(new ErrorResponseDto("Role not found"),HttpStatus.NOT_FOUND);
        }
        catch (PermissionIdNotFoundException e){
            return new ResponseEntity<>(new ErrorResponseDto("Permission not found"),HttpStatus.NOT_FOUND);
        }
        catch (InvalidInputException e){
            return new ResponseEntity<>(new ErrorResponseDto("Invalid data"), HttpStatus.BAD_REQUEST);
        }
        catch (UnknownErrorException e){
            return new ResponseEntity<>(new ErrorResponseDto("Unknown error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // TODO
        /*catch (HttpClientErrorException.Unauthorized e){
            return new ResponseEntity<>(new ErrorResponseDto("Unauthorized user"), HttpStatus.UNAUTHORIZED);
        }
        catch (HttpClientErrorException.Forbidden e){
            return new ResponseEntity<>(new ErrorResponseDto("Insufficient rights to create roles"), HttpStatus.FORBIDDEN);
        }*/
    }
}
