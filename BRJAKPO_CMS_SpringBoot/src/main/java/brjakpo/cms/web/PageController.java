/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brjakpo.cms.web;

import brjakpo.cms.db.MenuRepo;
import brjakpo.cms.db.PageRepo;
import brjakpo.cms.model.Menu;
import brjakpo.cms.model.Notification;
import brjakpo.cms.model.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Naruto
 */
@Controller
@RequestMapping("/page")
public class PageController {

    @Autowired
    MenuRepo menuRepo;

    @Autowired
    PageRepo pageRepo;

    @GetMapping("/index")
    public String Index(Model model, HttpServletRequest request, @RequestParam(value = "pageId", required = false) Integer pageId) {
        if (request.getSession().getAttribute("notification") != null) {
            model.addAttribute("notification", request.getSession().getAttribute("notification"));
            request.getSession().setAttribute("notification", null);
        }

        List<Menu> menus = menuRepo.findAll();
        if (pageId != null) {
            Page page = pageRepo.getOne(pageId);
            model.addAttribute("pageEdit", page);
        }

        model.addAttribute("menus", menus);
        return "page/index";
    }

    @PostMapping("/createOrUpdate")
    @Transactional
    public String CreateOrUpdate(@ModelAttribute Page page, HttpServletRequest request) {
        if (page.getMenuId() <= 0) {
            request.getSession().setAttribute("notification", new Notification("Izbornika mora biti odabran",
                    Notification.NotificationStatus.Error));
        } else if (page.getTitle()== null || page.getTitle().isEmpty()) {
            request.getSession().setAttribute("notification", new Notification("Naslov stranice mora biti popunjen",
                    Notification.NotificationStatus.Error));
        } else {
            boolean isUpdate = page.getPageId() > 0;
            pageRepo.save(page);
            if (isUpdate) {
                request.getSession().setAttribute("notification", new Notification("Stranica je uspješno uređena",
                        Notification.NotificationStatus.Success));
            } else {
                request.getSession().setAttribute("notification", new Notification("Stranica je uspješno napravljena",
                        Notification.NotificationStatus.Success));
            }
        }

        return "redirect:/page/index";
    }

    @GetMapping("/delete")
    public ModelAndView Delete(int pageId, HttpServletRequest request) {
        pageRepo.deleteById(pageId);
        request.getSession().setAttribute("notification", new Notification("Stranica je uspješno obrisana",
                Notification.NotificationStatus.Success));
        //return "redirect:/home/index";
        return new ModelAndView("redirect:/home/index");
    }

}
