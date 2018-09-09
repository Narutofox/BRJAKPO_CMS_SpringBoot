/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brjakpo.cms.web;

import brjakpo.cms.db.UserRepo;
import brjakpo.cms.db.UserRoleRepo;
import brjakpo.cms.model.LoginUser;
import brjakpo.cms.model.Notification;
import brjakpo.cms.model.User;
import brjakpo.cms.model.UserRole;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Naruto
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserRoleRepo userRoleRepo;

    @GetMapping("/index")
    public String index(Model model, HttpServletRequest request) {
        if (request.getSession().getAttribute("notification") != null) {
            model.addAttribute("notification", request.getSession().getAttribute("notification"));
            request.getSession().setAttribute("notification", null);
        }
        if (request.getSession().getServletContext().getAttribute("notification") != null) {
            model.addAttribute("notification", request.getSession().getServletContext().getAttribute("notification"));
            request.getSession().getServletContext().setAttribute("notification", null);
        }
        return "login";
    }

    @PostMapping("/perform_login")
    public String perform_login(@Valid @ModelAttribute User user, HttpServletRequest request) {
        User dbUser = userRepo.findByUsernameAndPassword(user.getUsername(), user.getPassword());

        if (dbUser == null || !dbUser.getEnabled()) {
            request.getSession().setAttribute("notification", new Notification("Neispravna kombinacija korisničkog imena i lozinke", Notification.NotificationStatus.Error));
            return "redirect:/login/index";
        } else {
            UserRole userRole = userRoleRepo.findByUsername(dbUser.getUsername());
            LoginUser loginUser = new LoginUser(dbUser.getId(), dbUser.getIme(), dbUser.getPrezime(), userRole.getRole());
            request.getSession().setAttribute("loginUser", loginUser);
            return "redirect:/home/index";
        }
    }

    @GetMapping("/logout")
    public String logout(Model model,HttpServletRequest request) {
        request.getSession().setAttribute("LoginUser", null);
        model.addAttribute("notification", new Notification("Uspješna odjava", Notification.NotificationStatus.Success));
        return "login";
    }
}
