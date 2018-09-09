/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brjakpo.cms.model;

/**
 *
 * @author Naruto
 */
public class MenuVM {

    private int menuId;
    private String name;
    private Integer parentId;
    private boolean hasSubMenus;

    public MenuVM(int menuId, String menuName, Integer parentId, boolean hasSubMenus) {
        this.menuId = menuId;
        this.name = menuName;
        this.parentId = parentId;
        this.hasSubMenus = hasSubMenus;
    }

    /**
     * @return the menuId
     */
    public int getMenuId() {
        return menuId;
    }

    /**
     * @param menuId the menuId to set
     */
    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    /**
     * @return the parentId
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the hasSubMenus
     */
    public boolean isHasSubMenus() {
        return hasSubMenus;
    }

    /**
     * @param hasSubMenus the hasSubMenus to set
     */
    public void setHasSubMenus(boolean hasSubMenus) {
        this.hasSubMenus = hasSubMenus;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
