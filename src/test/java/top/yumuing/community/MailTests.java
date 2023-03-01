package top.yumuing.community;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.yumuing.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTests {

    private static final Logger logger = LoggerFactory.getLogger(MailTests.class);

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;


    @Test
    public void testTextMail() {
        mailClient.sendMail("yuzhe780@126.com", "TEST", "Welcome.");
    }

    @Test
    public void testHtmlMail() {
        Context context = new Context();
        // 传入模板的对象数据
        context.setVariable("username", "sunday");
        // 调用模板引擎 "/mail/demo" 模板地址 context 传入数据
        String content = templateEngine.process("/mail/demo", context);

        mailClient.sendMail("yuzhe780@126.com", "HTML", content);

        logger.info("发送邮件成功！to："+ context.getVariable("username"));
    }

}
