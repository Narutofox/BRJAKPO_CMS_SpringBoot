/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brjakpo.cms.filters;

import brjakpo.cms.model.LoginUser;
import brjakpo.cms.model.Notification;
import brjakpo.cms.model.UserRole.UserRoles;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Naruto
 */
public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) sr;
        HttpServletResponse response = (HttpServletResponse) sr1;

        LoginUser loginUser = (LoginUser) request.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            request.getSession().getServletContext()
                    .setAttribute("notification", new Notification("Za pristup sadržaju molimo prijavite se.",
                            Notification.NotificationStatus.Error));
            response.sendRedirect(request.getContextPath() + "/login/index");
        } else if (!UserRoles.ROLE_ADMIN.toString().equals(loginUser.getRole())) {
            request.getSession().getServletContext()
                    .setAttribute("notification", new Notification("Nemate potrebna prava za pristup traženom sadržaju.",
                            Notification.NotificationStatus.Error));
            response.sendRedirect(request.getContextPath() + "/home/index");
        } else {
            try {
                fc.doFilter(request, response);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
    }

}
