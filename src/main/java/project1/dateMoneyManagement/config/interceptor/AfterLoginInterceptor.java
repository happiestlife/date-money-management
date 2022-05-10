package project1.dateMoneyManagement.config.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import project1.dateMoneyManagement.common.SessionKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AfterLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute(SessionKeys.LOGIN_SESSION) == null) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
}
