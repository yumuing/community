package top.yumuing.community.service;

import org.springframework.stereotype.Service;
import top.yumuing.community.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author yumuuing
* @description 针对表【user】的数据库操作Service
* @createDate 2023-02-08 00:47:08
*/
@Service
public interface UserService extends IService<User> {

    public User findUserById(int id);
}
