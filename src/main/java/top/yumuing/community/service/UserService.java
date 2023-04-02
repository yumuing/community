package top.yumuing.community.service;

import org.springframework.stereotype.Service;
import top.yumuing.community.entity.LoginTicket;
import top.yumuing.community.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import top.yumuing.community.util.CommunityConstant;

import java.util.Map;

/**
* @author yumuuing
* @description 针对表【user】的数据库操作Service
* @createDate 2023-02-08 00:47:08
*/
@Service
public interface UserService extends IService<User>, CommunityConstant {

    public User findUserById(int id);

    public Map<String, Object> register(User user);

    public int activation(int userId, String code);

    public Map<String, Object> login(String username, String password, int expiredSeconds);

    public void logout(String loginTicket);

    public LoginTicket getLoginTicket(String ticket);

    public int updateHeaderUrl(int userId,String headerUrl);

    public int updatePassword(int userId, String password);

    public int updateSalt(int userId, String salt);

    public User findUserByName(String username);
}
