package top.yumuing.community.util;

import org.springframework.stereotype.Component;
import top.yumuing.community.entity.User;

// 持有用户信息，代替 session 对象
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }

}
