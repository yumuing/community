package top.yumuing.community;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import top.yumuing.community.emtity.User;
import top.yumuing.community.mapper.UserMapper;
import top.yumuing.community.test.TestBeanManagement;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests implements ApplicationContextAware {

    private  ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public void testBeanConfig(){
        SimpleDateFormat simpleDateFormat = applicationContext.getBean("simpleDateFormat",SimpleDateFormat.class);
        System.out.println(simpleDateFormat.format(new Date()));
    }

    @Autowired
    public SimpleDateFormat simpleDateFormat;
    @Test
    public void testDI(){
        System.out.println(simpleDateFormat.format(new Date()));
    }

    @Test
    public void testBean(){
        TestBeanManagement testBeanManagement = applicationContext.getBean(TestBeanManagement.class);
        System.out.println(testBeanManagement);
    }

    @Resource
    DataSource dataSource;

    @Test
    void contextLoadsOne() throws Exception{
        System.out.println("获取的数据库连接为:"+dataSource.getConnection());
    }

    @Resource
    private UserMapper userMapper;

    @Test
    void contextLoadsTwo() {
        List<User> list = userMapper.selectList(null);
        list.forEach(item-> System.out.println(item));
    }

}
