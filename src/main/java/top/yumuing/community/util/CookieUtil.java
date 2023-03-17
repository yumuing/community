package top.yumuing.community.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

// 查询 cookie 中指定的信息
public class CookieUtil {
    public static String getValue(HttpServletRequest request, String cookieName){
        if (request == null || cookieName == null){
            throw new IllegalArgumentException("参数为空！");
        }


        Cookie[] cookies = request.getCookies();

        if (cookies != null){
            for (Cookie cookie : cookies){
                if (cookie.getName().equals(cookieName)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
