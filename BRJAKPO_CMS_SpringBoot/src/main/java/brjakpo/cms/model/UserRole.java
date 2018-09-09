/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brjakpo.cms.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Naruto
 */

@Entity
@Table(name="UserRole")
public class UserRole {
    @Id
    private int userRoleId ;
    private String username;
    private String role ;

    public int getUser_role_id() {
        return userRoleId;
    }

    public void setUser_role_id(int user_role_id) {
        this.userRoleId = user_role_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public enum UserRoles {
        ROLE_USER, ROLE_ADMIN;
    }
}
