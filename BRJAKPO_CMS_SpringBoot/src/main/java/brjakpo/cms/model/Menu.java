/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brjakpo.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Naruto
 */
@Entity
@Table(name = "Menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuId;
    @Size(min = 1, max = 50, message = "Ime izbornika mora imati između 1 i 50 znakova")
    @NotNull(message = "Ime izbornika mora biti popunjeno")
    @NotEmpty(message = "Ime izbornika mora biti popunjeno" )
    @Column(unique = true)
    private String menuName;
    private Integer parentId;

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return menuName;
    }

    public void setName(String name) {
        this.menuName = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

}
