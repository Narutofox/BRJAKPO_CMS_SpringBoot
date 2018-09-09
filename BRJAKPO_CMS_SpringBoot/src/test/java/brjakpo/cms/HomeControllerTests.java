/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brjakpo.cms;

import brjakpo.cms.config.JpaConfig;
import brjakpo.cms.db.MenuRepo;
import brjakpo.cms.db.PageRepo;
import brjakpo.cms.db.PostRepo;
import brjakpo.cms.db.UserRepo;
import brjakpo.cms.web.HomeController;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
//@SpringBootTest
//@WebMvcTest
//@ContextConfiguration(classes = {JpaConfig.class})
//@WebAppConfiguration
public class HomeControllerTests 
{    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MenuRepo menuRepo;

    @MockBean
    PageRepo pageRepo;

    @MockBean
    PostRepo postRepo;

    @MockBean
    UserRepo userRepo;
    
    @Test
    public void index() throws Exception {
         this.mockMvc.perform(get("/home/index"))
                 .andExpect(status().isOk())
                 .andExpect(view().name("index"))
                 .andExpect(model().attributeExists("mainMenus"));
    }
    
//    @Autowired
//    private HomeController controller;
//    
//    @Test
//    public void contexLoads() throws Exception {
//        assertThat(controller).isNotNull();
//    }
}
