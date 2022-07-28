package com.gfa.users.controllers;

import com.gfa.common.dtos.ErrorResponseDto;
import com.gfa.common.dtos.ResponseDto;
import com.gfa.common.dtos.RoleRequestDto;
import com.gfa.common.dtos.RoleResponseDto;
import com.gfa.common.dtos.StatusResponseDto;
import com.gfa.common.dtos.PermissionRequestDto;
import com.gfa.common.exceptions.EmptyBodyException;
import com.gfa.users.exceptions.DuplicateRoleException;
import com.gfa.users.exceptions.InvalidIdException;
import com.gfa.users.exceptions.UnknownErrorException;
import com.gfa.users.exceptions.IdNotFoundException;
import com.gfa.users.exceptions.PermissionIdNotFoundException;
import com.gfa.users.exceptions.InvalidInputException;
import com.gfa.users.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleRestController {

  private final RoleService roleService;

  @Autowired
  public RoleRestController(RoleService roleService) {
    this.roleService = roleService;
  }


  @GetMapping("/roles")
  public ResponseEntity<List<RoleResponseDto>> index() {
    return new ResponseEntity(roleService.index(), HttpStatus.OK);
  }

  @PostMapping("/roles")
  public ResponseEntity<? extends ResponseDto> store(@RequestBody RoleRequestDto dto) {

    RoleResponseDto role = roleService.store(dto);
    return new ResponseEntity<>(role, HttpStatus.CREATED);
    //TODO
    /*catch (HttpClientErrorException.Unauthorized e){
            return new ResponseEntity<>(new ErrorResponseDto("Unauthorized user"), HttpStatus.UNAUTHORIZED);
        }
        catch (HttpClientErrorException.Forbidden e){
            return new ResponseEntity<>(new ErrorResponseDto("Insufficient rights to create roles"), HttpStatus.FORBIDDEN);
        }*/
  }

  @GetMapping("/roles/{id}")
  public ResponseEntity<? extends ResponseDto> show(@PathVariable("id") Long id) {
    RoleResponseDto responseDto = roleService.show(id);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

  @PutMapping("/roles/{id}")
  public ResponseEntity<? extends ResponseDto> update(@PathVariable("id") Long id,
      @RequestBody RoleRequestDto dto) {

    RoleResponseDto responseDto = roleService.update(id, dto);
    return new ResponseEntity<>(responseDto, HttpStatus.resolve(200));
    // TODO
    /*catch (HttpClientErrorException.Unauthorized e){
            return new ResponseEntity<>(new ErrorResponseDto("Unauthorized user"), HttpStatus.UNAUTHORIZED);
        }
        catch (HttpClientErrorException.Forbidden e){
            return new ResponseEntity<>(new ErrorResponseDto("Insufficient rights to create roles"), HttpStatus.FORBIDDEN);
        }*/
  }

  @DeleteMapping("/roles/{id}")
  public ResponseEntity<? extends ResponseDto> destroy(@PathVariable("id") Long id) {

    StatusResponseDto status = roleService.destroy(id);
    return new ResponseEntity<>(status, HttpStatus.resolve(201));
    // TODO
    /*catch (HttpClientErrorException.Unauthorized e){
            return new ResponseEntity<>(new ErrorResponseDto("Unauthorized user"), HttpStatus.UNAUTHORIZED);
        }
        catch (HttpClientErrorException.Forbidden e){
            return new ResponseEntity<>(new ErrorResponseDto("Insufficient rights to create roles"), HttpStatus.FORBIDDEN);
        }*/
  }

  @PostMapping("roles/{id}/permissions")
  public ResponseEntity<? extends ResponseDto> storePermission(@PathVariable("id") Long id,
      @RequestBody PermissionRequestDto permission) {

    StatusResponseDto status = roleService.storePermission(id, permission);
    return new ResponseEntity<>(status, HttpStatus.resolve(200));
    // TODO
    /*catch (HttpClientErrorException.Unauthorized e){
            return new ResponseEntity<>(new ErrorResponseDto("Unauthorized user"), HttpStatus.UNAUTHORIZED);
        }
        catch (HttpClientErrorException.Forbidden e){
            return new ResponseEntity<>(new ErrorResponseDto("Insufficient rights to create roles"), HttpStatus.FORBIDDEN);
        }*/
  }

  @DeleteMapping("roles/{id}/permissions/{permission_id}")
  public ResponseEntity<? extends ResponseDto> destroyPermission(@PathVariable("id") Long id,
      @PathVariable("permissionId") Long permissionId) {

    roleService.destroyPermission(id, permissionId);
    return new ResponseEntity<>(HttpStatus.resolve(204));
    // TODO
    /*catch (HttpClientErrorException.Unauthorized e){
            return new ResponseEntity<>(new ErrorResponseDto("Unauthorized user"), HttpStatus.UNAUTHORIZED);
        }
        catch (HttpClientErrorException.Forbidden e){
            return new ResponseEntity<>(new ErrorResponseDto("Insufficient rights to create roles"), HttpStatus.FORBIDDEN);
        }*/
  }
}
