package com.gfa.users.models;

import org.jetbrains.annotations.NotNull;
import com.gfa.users.models.Permission;
import com.gfa.users.models.User;
import com.gfa.users.models.Team;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,name = "id")
    @NotNull
    private Long id;
    @NotNull
    @Column(unique = true,name = "role")
    private String role;

    @ManyToMany
    @JoinTable(
            name = "role_permission_db",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    Set<Permission> permissions;

    @ManyToMany
    @JoinTable(
            name = "user_role_db",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    Set<User> users;

    @ManyToMany
    @JoinTable(
            name = "team_role_db",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    Set<Team> teams;
    
    public Role() {
    }
    public Role(@NotNull String role) {
        this.role = role;
    }

    @NotNull
    public Long getId() {
        return id;
    }

    public String getRole(){
        return role;
    }

    public void addPermission(Permission p){
        permissions.add(p);
    }

    public void removePermission(Permission p){
        permissions.remove(p);
    }

    public Boolean is(Role r){
        return (role.equals(r.role));
    }

    /*public Boolean can(Permission permission){
        return can(permission.getAbility);
    }

    public Boolean can(String ability){
        for(Permission permission : permissions){
            if(ability.equals(permission.getAbility)){
                return true;
            }
        }
        return false;
    }*/
}
