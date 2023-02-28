package top.yumuing.community;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import top.yumuing.community.entity.DiscussPost;
import top.yumuing.community.entity.User;
import top.yumuing.community.mapper.DiscussPostMapper;
import top.yumuing.community.mapper.UserMapper;

import java.util.List;


@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests implements ApplicationContextAware {

    private  ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Resource
    public DiscussPostMapper discussPostMapper;

    @Resource
    public UserMapper userMapper;

    @Test
    public void selectTest(){
        List<DiscussPost> list= discussPostMapper.selectDiscussPosts(149,0,10);
        for (DiscussPost post:list){
            System.out.println(post);
        }
        int row = discussPostMapper.selectDiscussPostRows(149);
        System.out.println(row);

        User user = userMapper.selectOneById(115);
        System.out.println(user);
    }

}
