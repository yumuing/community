package top.yumuing.community;

import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import top.yumuing.community.entity.User;
import top.yumuing.community.service.UserService;
import top.yumuing.community.util.CommunityUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class LoggerTest {

    private static final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Resource
    @Qualifier("userServiceImpl")
    private UserService userServiceImpl;

    @Test
    public void testLogger(){
        System.out.println(logger.getName());
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
    }

    @Test
    public void password(){
        User user = userServiceImpl.findUserById(159);
        System.out.println(CommunityUtil.md5("123" + user.getSalt()));
    }

}
