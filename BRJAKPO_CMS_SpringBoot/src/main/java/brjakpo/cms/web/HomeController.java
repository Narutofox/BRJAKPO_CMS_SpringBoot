/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brjakpo.cms.web;

import brjakpo.cms.db.MenuRepo;
import brjakpo.cms.db.PageRepo;
import brjakpo.cms.db.PostRepo;
import brjakpo.cms.db.UserRepo;
import brjakpo.cms.model.LoginUser;
import brjakpo.cms.model.Menu;
import brjakpo.cms.model.MenuVM;
import brjakpo.cms.model.Page;
import brjakpo.cms.model.PageVM;
import brjakpo.cms.model.Post;
import brjakpo.cms.model.PostVM;
import brjakpo.cms.model.User;
import brjakpo.cms.model.UserRole.UserRoles;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Naruto
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    MenuRepo menuRepo;

    @Autowired
    PageRepo pageRepo;

    @Autowired
    PostRepo postRepo;

    @Autowired
    UserRepo userRepo;

    @GetMapping("/index")
    public String index(Model model, HttpServletRequest request) {
        List<Menu> mainMenus = menuRepo.findByParentIdIsNull();
        List<MenuVM> mainMenusVMs = new ArrayList<MenuVM>();
        for (Menu menu : mainMenus) {
            mainMenusVMs.add(
                    new MenuVM(menu.getMenuId(), menu.getName(), menu.getParentId(), menuRepo.hasSubMenus(menu.getMenuId()))
            );
        }
        if (request.getSession().getAttribute("notification") != null) {
            model.addAttribute("notification", request.getSession().getAttribute("notification"));
            request.getSession().setAttribute("notification", null);
        }
        if (request.getSession().getServletContext().getAttribute("notification") != null) {
            model.addAttribute("notification", request.getSession().getServletContext().getAttribute("notification"));
            request.getSession().getServletContext().setAttribute("notification", null);
        }
        
        model.addAttribute("mainMenus", mainMenusVMs);
        return "index";
    }

    @GetMapping(value = "/getSubMenus", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody List<MenuVM> getSubMenus(int menuId) {
        List<Menu> subMenus = menuRepo.findByParentId(menuId);
        List<MenuVM> subMenusVMs = new ArrayList<>();
        for (Menu menu : subMenus) {
            subMenusVMs.add(
                    new MenuVM(menu.getMenuId(), menu.getName(), menu.getParentId(), menuRepo.hasSubMenus(menu.getMenuId()))
            );
        }
        return subMenusVMs;
    }

    @GetMapping(value = "/getMenuPages", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    List<PageVM> getMenuPages(int menuId) {
        List<Page> pages = pageRepo.findByMenuId(menuId);
        List<PageVM> pageVMs = new ArrayList<>();
        for (Page page : pages) {
            pageVMs.add(new PageVM(page.getPageId(), page.getTitle()));
        }
        return pageVMs;
    }

    @GetMapping(value = "/pageDetails", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody Page pageDetails(int pageId) {
        Page page = pageRepo.getOne(pageId);
        return page;
    }

    @GetMapping(value = "/pagePosts", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody List<PostVM> pagePosts(int pageId, HttpServletRequest request) {
        List<PostVM> postsVM = new ArrayList<>();
        List<Post> posts = postRepo.findByPageIdOrderByInputDateDesc(pageId);
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("loginUser");

        for (Post post : posts) {
            User user = userRepo.getOne(post.getUserId());
            postsVM.add(new PostVM(post.getPostId(), post.getContent(), user.getIme(), user.getPrezime(),
                    loginUser.getId() == post.getUserId()
                    || loginUser.getRole().equals(UserRoles.ROLE_ADMIN.toString())));
        }

        return postsVM;
    }

    @PostMapping(value = "/newPagePost")
    @Transactional
    public @ResponseBody void NewPagePost(String postContent, int pageId, HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("loginUser");
        Post newPost = new Post(pageId, loginUser.getId(), postContent);
        postRepo.save(newPost);
    }

    @PostMapping(value = "/deletePost", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional public @ResponseBody int deletePost(int postId, HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("loginUser");
        Post post = postRepo.getOne(postId);

        if (loginUser.getId() == post.getUserId() || loginUser.getRole().equals(UserRoles.ROLE_ADMIN.toString())) {
            postRepo.deleteById(postId);
        }

        return post.getPageId();
    }

}
