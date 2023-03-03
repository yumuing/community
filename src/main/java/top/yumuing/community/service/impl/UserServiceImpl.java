package top.yumuing.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import top.yumuing.community.entity.User;
import top.yumuing.community.service.UserService;
import top.yumuing.community.mapper.UserMapper;
import org.springframework.stereotype.Service;
import top.yumuing.community.util.CommunityUtil;
import top.yumuing.community.util.MailClient;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
* @author yumuuing
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-02-08 00:47:08
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${community.domain.path}")
    private String domain;

    @Override
    public User findUserById(int id) {
        return userMapper.selectOneById(id);
    }

    @Override
    public Map<String, Object> register(User user){

        Map<String, Object> map = new HashMap<>();
        //空值处理
        if (user == null){
            throw new IllegalArgumentException("参数不能为空");
        }
        if (StringUtils.isBlank(user.getUsername())){
            map.put("usernameMsg", "账号不能为空");
            return map;
        }

        if (StringUtils.isBlank(user.getPassword())){
            map.put("passwordMsg", "密码不能为空");
            return map;
        }

        if (StringUtils.isBlank(user.getEmail())){
            map.put("emailMsg", "邮箱不能为空");
            return map;
        }
        // 验证账号是否存在
        User ExistedUser = userMapper.selectOneByUsername(user.getUsername());
        System.out.println(ExistedUser != null);
        if (ExistedUser != null){
            map.put("usernameMsg","该账号已存在");
            return map;
        }

        // 验证邮箱是否存在
        ExistedUser = userMapper.selectOneByEmail(user.getEmail());
        if (ExistedUser!=null){
            map.put("emailMsg","该邮箱已被注册");
            return map;
        }

        // 注册用户
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5(user.getPassword()+user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        //激活码
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png)",new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertAll(user);

        // 发送激活邮件
        Context context = new Context();
        context.setVariable("email",user.getEmail());
        // http://localhost:8888/activation/userId(变量)/激活码(变量)
        String url = domain + "/activation/" + user.getId() + "/" + user.getActivationCode();
        System.out.println(url);
        context.setVariable("url",url);
        String content = templateEngine.process("/mail/activation",context);
        mailClient.sendMail(user.getEmail(),"激活账号",content);
        return map;
    }

    // 激活账户
    public int activation(int userId, String code){
        User user = userMapper.selectOneById(userId);
        if (user == null){
            return ACTIVATION_NULL;
        } else if (user.getStatus()==1){
            return ACTIVATION_REPEAT;
        } else if (user.getActivationCode().equals(code)) {
            userMapper.updateStatusById(1,userId);
            return ACTIVATION_SUCCESS;
        } else {
          return ACTIVATION_FAILURE;
        }

    }

}




