/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brjakpo.cms;

import brjakpo.cms.db.MenuRepo;
import brjakpo.cms.db.PageRepo;
import brjakpo.cms.model.Menu;
import brjakpo.cms.model.Notification;
import brjakpo.cms.model.Page;
import brjakpo.cms.web.PageController;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 *
 * @author Naruto
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PageController.class)
public class PageControllerTests {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    MenuRepo menuRepo;
   
    @MockBean
    PageRepo pageRepo;
    
    
    @Test
    public void index() throws Exception {
        List<Menu> mainMenus = new ArrayList<>();
        
        Menu menu = new Menu();
        menu.setName("Test");
        menu.setMenuId(1);
        mainMenus.add(menu);
        
        when(menuRepo.findAll()).thenReturn(mainMenus);
        
        this.mockMvc.perform(get("/page/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("page/index"))
                .andExpect(model().attributeExists("menus"))
                .andExpect(model().attribute("menus",mainMenus));
    }
    
    @Test
    public void index_pageEdit() throws Exception {
        List<Menu> mainMenus = new ArrayList<>();
        
        Menu menu = new Menu();
        menu.setName("Test");
        menu.setMenuId(1);
        mainMenus.add(menu);
        
        when(menuRepo.findAll()).thenReturn(mainMenus);
        
        Page page = new Page();
        page.setPageId(2);
        
        when(pageRepo.getOne(page.getPageId())).thenReturn(page);
        
        this.mockMvc.perform(get("/page/index").param("pageId",String.valueOf(page.getPageId())))
                .andExpect(status().isOk())
                .andExpect(view().name("page/index"))
                .andExpect(model().attributeExists("menus"))
                .andExpect(model().attribute("menus",mainMenus))
                .andExpect(model().attributeExists("pageEdit"))
                .andExpect(model().attribute("pageEdit",page));
    }
    
    
    @Test
    public void createOrUpdate_menuIdIsRequired() throws Exception {
       Notification notification = new Notification("Izbornika mora biti odabran",Notification.NotificationStatus.Error);
       
        this.mockMvc.perform(post("/page/createOrUpdate"))
                .andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttribute("notification", hasProperty("cssClass", is(notification.getCssClass()))))
                .andExpect(request().sessionAttribute("notification", hasProperty("text", is(notification.getText()))))
                .andExpect(view().name("redirect:/page/index"));
    }
    
    @Test
    public void createOrUpdate_pageTitleIsRequired() throws Exception {
        
        Notification notification = new Notification("Naslov stranice mora biti popunjen",Notification.NotificationStatus.Error);
        
        Page page = new Page();
        page.setMenuId(2);
        
        this.mockMvc.perform(post("/page/createOrUpdate").param("menuId", String.valueOf(page.getMenuId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttribute("notification", hasProperty("cssClass", is(notification.getCssClass()))))
                .andExpect(request().sessionAttribute("notification", hasProperty("text", is(notification.getText()))))
                .andExpect(view().name("redirect:/page/index"));
    }
    
    
    @Test
    public void createOrUpdate_update() throws Exception {
        
        Notification notification = new Notification("Stranica je uspješno uređena", Notification.NotificationStatus.Success);
        
        Page page = new Page();
        page.setMenuId(1);
        page.setPageId(2);
        page.setTitle("Bok");

        
        this.mockMvc.perform(post("/page/createOrUpdate")
                    .param("pageId",String.valueOf(page.getPageId()))
                    .param("title", page.getTitle())
                    .param("menuId", String.valueOf(page.getMenuId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttribute("notification", hasProperty("cssClass", is(notification.getCssClass()))))
                .andExpect(request().sessionAttribute("notification", hasProperty("text", is(notification.getText()))))
                .andExpect(view().name("redirect:/page/index"));
    }
    
    
    @Test
    public void createOrUpdate_create() throws Exception {
        
        Notification notification = new Notification("Stranica je uspješno napravljena", Notification.NotificationStatus.Success);
        
        Page page = new Page();
        page.setMenuId(1);
        page.setPageId(0);
        page.setTitle("Bok");

        
        this.mockMvc.perform(post("/page/createOrUpdate")
                    .param("pageId",String.valueOf(page.getPageId()))
                    .param("title", page.getTitle())
                    .param("menuId", String.valueOf(page.getMenuId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttribute("notification", hasProperty("cssClass", is(notification.getCssClass()))))
                .andExpect(request().sessionAttribute("notification", hasProperty("text", is(notification.getText()))))
                .andExpect(view().name("redirect:/page/index"));
    }
    
    
    @Test
    public void delete() throws Exception {
        Notification notification = new Notification("Stranica je uspješno obrisana", Notification.NotificationStatus.Success);
        
        Page page = new Page();
        page.setPageId(2);
        
        this.mockMvc.perform(get("/page/delete").param("pageId",  String.valueOf(page.getPageId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttribute("notification", hasProperty("cssClass", is(notification.getCssClass()))))
                .andExpect(request().sessionAttribute("notification", hasProperty("text", is(notification.getText()))))
                .andExpect(view().name("redirect:/home/index"));
    }
}
