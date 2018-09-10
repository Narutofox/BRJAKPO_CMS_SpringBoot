/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brjakpo.cms;


import brjakpo.cms.db.MenuRepo;
import brjakpo.cms.model.Menu;
import brjakpo.cms.model.Notification;
import brjakpo.cms.web.MainMenuController;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 *
 * @author Naruto
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MainMenuController.class)
public class MainMenuControllerTests {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    MenuRepo menuRepo;
    
    @Test
    public void index() throws Exception {
        List<Menu> mainMenus = new ArrayList<>();
        
        Menu menu = new Menu();
        menu.setName("Test");
        menu.setMenuId(1);
        mainMenus.add(menu);
        
        when(menuRepo.findByParentIdIsNull()).thenReturn(mainMenus);
        
        this.mockMvc.perform(get("/mainMenu/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("mainMenu/index"))
                .andExpect(model().attributeExists("mainMenus"))
                .andExpect(model().attribute("mainMenus",mainMenus));
    }
     
    @Test
    public void createOrUpdate_menuNameIsRequired() throws Exception {
       Notification notification = new Notification("Ime izbornika mora biti popunjeno",Notification.NotificationStatus.Error);
       
        this.mockMvc.perform(post("/mainMenu/createOrUpdate"))
                .andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttribute("notification", hasProperty("cssClass", is(notification.getCssClass()))))
                .andExpect(request().sessionAttribute("notification", hasProperty("text", is(notification.getText()))))
                .andExpect(view().name("redirect:/mainMenu/index"));
    }
    
    @Test
    public void createOrUpdate_menuNameMustBeUniq() throws Exception {
        
        Notification notification = new Notification("Izbornik sa danim nazivom već postoji",Notification.NotificationStatus.Error);
        
        Menu menu = new Menu();
        menu.setName("Test");
        menu.setMenuId(1);
        when(menuRepo.existsByMenuName(menu.getName())).thenReturn(true);
        
        this.mockMvc.perform(post("/mainMenu/createOrUpdate").param("name", menu.getName()))
                .andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttribute("notification", hasProperty("cssClass", is(notification.getCssClass()))))
                .andExpect(request().sessionAttribute("notification", hasProperty("text", is(notification.getText()))))
                .andExpect(view().name("redirect:/mainMenu/index"));
    }
    
    
    @Test
    public void createOrUpdate_update() throws Exception {
        
        Notification notification = new Notification("Izbornik je uspješno uređen", Notification.NotificationStatus.Success);
        
        Menu menu = new Menu();
        menu.setName("Test");
        menu.setMenuId(1);
        when(menuRepo.existsByMenuName(menu.getName())).thenReturn(false);
        
        this.mockMvc.perform(post("/mainMenu/createOrUpdate").param("name", menu.getName()).param("menuId",  String.valueOf(menu.getMenuId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttribute("notification", hasProperty("cssClass", is(notification.getCssClass()))))
                .andExpect(request().sessionAttribute("notification", hasProperty("text", is(notification.getText()))))
                .andExpect(view().name("redirect:/mainMenu/index"));
    }
    
    
        @Test
    public void createOrUpdate_create() throws Exception {
        
        Notification notification = new Notification("Izbornik je uspješno napravljen", Notification.NotificationStatus.Success);
        
        Menu menu = new Menu();
        menu.setName("Test");
        menu.setMenuId(0);
        when(menuRepo.existsByMenuName(menu.getName())).thenReturn(false);
        
        this.mockMvc.perform(post("/mainMenu/createOrUpdate").param("name", menu.getName()).param("menuId",  String.valueOf(menu.getMenuId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttribute("notification", hasProperty("cssClass", is(notification.getCssClass()))))
                .andExpect(request().sessionAttribute("notification", hasProperty("text", is(notification.getText()))))
                .andExpect(view().name("redirect:/mainMenu/index"));
    }
    
    @Test
    public void delete() throws Exception {
        Notification notification = new Notification("Izbornik je uspješno obrisan", Notification.NotificationStatus.Success);
        
        Menu menu = new Menu();
        menu.setName("Test");
        menu.setMenuId(1);
        
        this.mockMvc.perform(get("/mainMenu/delete").param("menuId",  String.valueOf(menu.getMenuId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttribute("notification", hasProperty("cssClass", is(notification.getCssClass()))))
                .andExpect(request().sessionAttribute("notification", hasProperty("text", is(notification.getText()))))
                .andExpect(view().name("redirect:/mainMenu/index"));
    }
}
