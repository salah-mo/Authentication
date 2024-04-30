package com.java.authentication.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "role_permissions")
@IdClass(RolePermission.class)
public class RolePermission implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Id
    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;

    // Default constructor
    public RolePermission() {
    }

    // Getters and setters
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}