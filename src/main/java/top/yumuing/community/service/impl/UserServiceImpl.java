package top.yumuing.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.yumuing.community.entity.User;
import top.yumuing.community.service.UserService;
import top.yumuing.community.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author yumuuing
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-02-08 00:47:08
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




