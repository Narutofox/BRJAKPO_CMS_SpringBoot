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
@RequestMapping("/mainMenu")
public class MainMenuController {

    @Autowired
    MenuRepo menuRepo;

    @GetMapping("/index")
    public String Index(Model model, HttpServletRequest request) {
        if (request.getSession().getAttribute("notification") != null) {
            model.addAttribute("notification", request.getSession().getAttribute("notification"));
            request.getSession().setAttribute("notification", null);
        }

        //Glavni izbornici su oni koji nemaju roditelja
        List<Menu> mainMenuList = menuRepo.findByParentIdIsNull();
        model.addAttribute("mainMenus", mainMenuList);
        return "mainMenu/index";
    }

    @PostMapping("/createOrUpdate")
    @Transactional
    public String CreateOrUpdate(@ModelAttribute Menu mainMenu, HttpServletRequest request) {
        if (mainMenu.getName() == null || mainMenu.getName().isEmpty()) {
            request.getSession().setAttribute("notification", new Notification("Ime izbornika mora biti popunjeno",
                            Notification.NotificationStatus.Error));
        } 
        else if (menuRepo.existsByMenuName(mainMenu.getName())) {
            request.getSession()
                    .setAttribute("notification", new Notification("Izbornik sa danim nazivom već postoji",
                            Notification.NotificationStatus.Error));
        }
        else {
            boolean isUpdate = mainMenu.getMenuId() > 0;
            menuRepo.save(mainMenu);
            if (isUpdate) {
                request.getSession().setAttribute("notification", new Notification("Izbornik je uspješno uređen",
                        Notification.NotificationStatus.Success));
            } else {
                request.getSession().setAttribute("notification", new Notification("Izbornik je uspješno napravljen",
                        Notification.NotificationStatus.Success));
            }
        }

        return "redirect:/mainMenu/index";
    }

    @GetMapping("/delete")
    public String Delete(int menuId, HttpServletRequest request) {
        menuRepo.deleteById(menuId);
        request.getSession().setAttribute("notification", new Notification("Izbornik je uspješno obrisan",
                Notification.NotificationStatus.Success));
        return "redirect:/mainMenu/index";
    }
}
