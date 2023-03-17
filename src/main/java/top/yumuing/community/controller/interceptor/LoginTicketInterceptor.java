package top.yumuing.community.controller.interceptor;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.yumuing.community.entity.LoginTicket;
import top.yumuing.community.entity.User;
import top.yumuing.community.service.UserService;
import top.yumuing.community.util.CookieUtil;
import top.yumuing.community.util.HostHolder;

import java.util.Date;

// 让请求持有用户
@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Resource
    @Qualifier("userServiceImpl")
    private UserService userServiceImpl;

    @Autowired
    private HostHolder hostHolder;

    // 拦截需要登录却未登录的接口
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 查询凭证
        String loginTicket = CookieUtil.getValue(request, "loginTicket");

        if (loginTicket != null){
            LoginTicket userTicket = userServiceImpl.getLoginTicket(loginTicket);
            // 查询凭证是否有效
            if (userTicket != null && userTicket.getStatus() ==1 && userTicket.getExpired().after(new Date())){
                // 根据凭证查询用户
                User user = userServiceImpl.findUserById(userTicket.getUserId());
                // 多线程情况下，不能使用简单的公共方法或者容器可能会发生冲突，必须实现线程隔离
                // 在本次请求中持有用户
                hostHolder.setUser(user);
            }
        }
        return true;
    }

    // 往模板里存入用户
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null){
            modelAndView.addObject("loginUser", user);
        }
    }

    // 请求结束，清理用户
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
