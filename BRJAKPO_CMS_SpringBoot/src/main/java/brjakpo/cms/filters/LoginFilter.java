/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brjakpo.cms.filters;

import brjakpo.cms.model.LoginUser;
import brjakpo.cms.model.Notification;
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
public class LoginFilter implements Filter {

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
        } else {
            try {
                fc.doFilter(request, response);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

    }

    @Override
    public void destroy() {
    }
}
