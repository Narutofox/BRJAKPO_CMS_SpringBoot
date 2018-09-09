/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brjakpo.cms;

import brjakpo.cms.db.UserRepo;
import brjakpo.cms.db.UserRoleRepo;
import brjakpo.cms.model.User;
import brjakpo.cms.model.UserRole;
import brjakpo.cms.web.LoginController;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserRoleRepo userRoleRepo;

    @MockBean
    UserRepo userRepo;

    @Test
    public void index() throws Exception {
        this.mockMvc.perform(get("/login/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void perform_login_userIsNull() throws Exception {
        this.mockMvc.perform(post("/login/perform_login"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login/index"));
    }
    
    @Test
    public void perform_login_userDisabled() throws Exception {      
        User found = new User();
        found.setUsername("Hero");
        found.setPassword("123");
        found.setEnabled(false);

        when(userRepo.findByUsernameAndPassword(found.getUsername(),found.getPassword())).thenReturn(found);
        this.mockMvc.perform(post("/login/perform_login").param("username", found.getUsername()).param("password", found.getPassword()))               
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login/index"));       
    }
    
    @Test
    public void perform_login_success() throws Exception {      
        User found = new User();
        found.setUsername("Hero");
        found.setPassword("123");   
        found.setEnabled(true);
        
        when(userRepo.findByUsernameAndPassword(found.getUsername(),found.getPassword())).thenReturn(found);      
        when(userRoleRepo.findByUsername(found.getUsername())).thenReturn(new UserRole());
        
        this.mockMvc.perform(post("/login/perform_login").param("username", found.getUsername()).param("password", found.getPassword()))               
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/home/index"));
    }

    @Test
    public void logout() throws Exception {
        this.mockMvc.perform(get("/login/logout"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}
