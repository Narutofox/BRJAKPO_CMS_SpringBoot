/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brjakpo.cms.db;

import brjakpo.cms.model.Menu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Naruto
 */

public interface MenuRepo extends JpaRepository<Menu, Integer>{
//    public static final String MENUS_WITH_SUB_MENUS = "WITH RECURSIVE MyTree AS (\n" +
//"    SELECT * FROM Menus WHERE parentId IS NULL\n" +
//"    UNION ALL\n" +
//"    SELECT m.* FROM Menus AS m JOIN MyTree AS t ON m.parentId = t.menuId   \n" +
//")\n" +
//"SELECT * FROM MyTree;";
    
    List<Menu> findByParentIdIsNull();
    List<Menu> findByParentIdIsNotNull();
    List<Menu> findByParentId(int parentId);
    boolean existsByMenuName(String menuName);
    @Query("select case when count(m) > 0 then true else false end from Menu as m where m.parentId = ?1")
    boolean hasSubMenus(int menuId);
//    @Query(value = MENUS_WITH_SUB_MENUS, nativeQuery = true)
//    List<Menu> findAllMenusWithSubMenus();
}
