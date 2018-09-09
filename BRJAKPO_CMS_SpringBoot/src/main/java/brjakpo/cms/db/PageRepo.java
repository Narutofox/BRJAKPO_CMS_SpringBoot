/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brjakpo.cms.db;

import brjakpo.cms.model.Menu;
import brjakpo.cms.model.Page;
import brjakpo.cms.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Naruto
 */
public interface PageRepo extends JpaRepository<Page, Integer>{

    public List<Page> findByMenuId(int menuId);
    
}
