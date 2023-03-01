package top.yumuing.community.util;

import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
public class MailClient {
    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    @Autowired(required = false)
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    public void sendMail(String to, String subject, String content){
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            // 发送主体
            mimeMessageHelper.setFrom(mailFrom);
            // 发送对象
            mimeMessageHelper.setTo(to);
            // 发送主题
            mimeMessageHelper.setSubject(subject);
            // 允许发送 html
            mimeMessageHelper.setText(content, true);
            // 发送邮件
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            logger.info("邮件发送成功！");
        }catch (MessagingException e){
            logger.error("发送邮件失败！");
        }
    }
}
