package top.yumuing.community;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import top.yumuing.community.util.CommunityUtil;
import top.yumuing.community.util.MailClient;
import top.yumuing.community.util.SensitiveWordUtil;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class BeanTest {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private SensitiveWordBs sensitiveWordBs;

    @Autowired
    private SensitiveWordUtil sensitiveWordUtil;

    @Test
    public void testTextMail() {
        mailClient.sendMail("yuzhe780@126.com", "TEST", "Welcome.");
    }

    @Test
    public void utilTest(){
        String result = sensitiveWordBs.replace("法网恢恢 哇 nnd 复活");
        System.out.println(result);
    }

    @Test
    public void utilTest02(){
        String result = sensitiveWordUtil.replace("法网恢恢 哇 nnd 毛爷爷复活");
        System.out.println(result);
    }

    @Test
    public void jsonTest(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","zhan");
        map.put("age",18);
        System.out.println(CommunityUtil.getJsonString(200, "ok", map));
    }
}
