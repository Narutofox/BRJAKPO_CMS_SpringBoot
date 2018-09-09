/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brjakpo.cms.web;

import brjakpo.cms.db.MenuRepo;
import brjakpo.cms.model.Menu;
import brjakpo.cms.model.Notification;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
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
@RequestMapping("/subMenu")
public class SubMenuController {

    @Autowired
    MenuRepo menuRepo;

    @GetMapping("/index")
    public String index(Model model, HttpServletRequest request) {
        List<Menu> parentMenuList = menuRepo.findAll();
        //Pod izbornici su oni koji imaju roditelja
        List<Menu> subMenuList = menuRepo.findByParentIdIsNotNull();

        if (request.getSession().getAttribute("notification") != null) {
            model.addAttribute("notification", request.getSession().getAttribute("notification"));
            request.getSession().setAttribute("notification", null);
        }

        model.addAttribute("parentMenus", parentMenuList);
        model.addAttribute("subMenus", subMenuList);
        return "subMenu/index";
    }

    @PostMapping("/createOrUpdate")
    @Transactional
    public String CreateOrUpdate(@ModelAttribute Menu subMenu, HttpServletRequest request) {
        if (subMenu.getName() == null || subMenu.getName().isEmpty()) {
            request.getSession().setAttribute("notification", new Notification("Ime izbornika mora biti popunjeno",
                    Notification.NotificationStatus.Error));
        } else if (subMenu.getParentId() == null || subMenu.getParentId() <= 0) {
            request.getSession().setAttribute("notification", new Notification("Pod izbornici moraju imati roditelja",
                    Notification.NotificationStatus.Error));
        } else {
            boolean isUpdate = subMenu.getMenuId() > 0;
            menuRepo.save(subMenu);

            if (isUpdate) {
                request.getSession().setAttribute("notification", new Notification("Pod izbornik je uspješno uređen",
                        Notification.NotificationStatus.Success));
            } else {
                request.getSession().setAttribute("notification", new Notification("Pod izbornik je uspješno napravljen",
                        Notification.NotificationStatus.Success));
            }
        }

        return "redirect:/subMenu/index";
    }

    @GetMapping("/delete")
    public String delete(@ModelAttribute Menu mainMenu, Model model, HttpServletRequest request) {
        menuRepo.delete(mainMenu);
        request.getSession().setAttribute("notification", new Notification("Pod izbornik je uspješno obrisan",
                Notification.NotificationStatus.Success));
        return "redirect:/subMenu/index";
    }
}
