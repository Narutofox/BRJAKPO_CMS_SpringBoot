/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brjakpo.cms;

import brjakpo.cms.db.MenuRepo;
import brjakpo.cms.db.PageRepo;
import brjakpo.cms.db.PostRepo;
import brjakpo.cms.db.UserRepo;
import brjakpo.cms.model.LoginUser;
import brjakpo.cms.model.Page;
import brjakpo.cms.model.Post;
import brjakpo.cms.model.UserRole;
import brjakpo.cms.web.HomeController;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
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
    
    @Test
    public void getSubMenus() throws Exception {       
         this.mockMvc.perform(get("/home/getSubMenus/?menuId=1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))               
                .andExpect(status().isOk());
    }
    
    @Test
    public void getMenuPages() throws Exception {       
         this.mockMvc.perform(get("/home/getMenuPages/?menuId=1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))               
                .andExpect(status().isOk());
    }
    
    @Test
    public void pageDetails() throws Exception {   
        
        Page found = new Page();
        found.setPageId(1);
        found.setContent("Lorem ipsum");
        found.setTitle("Foo");
        found.setAllowComments(false);
        found.setMenuId(7);

        when(pageRepo.getOne(found.getPageId())).thenReturn(found);
        
        Gson gson = new Gson();
        String json = gson.toJson(found);
    
         this.mockMvc.perform(get("/home/pageDetails/?pageId="+String.valueOf(found.getPageId())).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))               
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }  
    
    
    @Test
    public void deletePost() throws Exception {   
        
        Post found = new Post();
        found.setPostId(5);
        found.setPageId(2);
        found.setContent("Foo");
        found.setUserId(2);

        when(postRepo.getOne(found.getPostId())).thenReturn(found);
        
        Gson gson = new Gson();
        String json = gson.toJson(found.getPageId());
        MockHttpSession session = new MockHttpSession();
        LoginUser loginUser = new LoginUser(7, "Naruto", "Uzumaki",UserRole.UserRoles.ROLE_ADMIN.toString());
        
        session.setAttribute("loginUser", loginUser);
         this.mockMvc.perform(post("/home/deletePost").
                                contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .param("postId", String.valueOf(found.getPostId()))
                                .session(session)
                            )               
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    } 
}
