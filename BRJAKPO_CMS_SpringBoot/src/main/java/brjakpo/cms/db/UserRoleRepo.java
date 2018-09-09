/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brjakpo.cms.db;

import brjakpo.cms.model.User;
import brjakpo.cms.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Naruto
 */
public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {
    UserRole findByUsername(String username);
}
