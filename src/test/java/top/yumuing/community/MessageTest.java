package top.yumuing.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import top.yumuing.community.entity.Message;
import top.yumuing.community.mapper.MessageMapper;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MessageTest {

    @Autowired
    private MessageMapper messageMapper;

    @Test
    public void testMessage(){
        List<Message> list = messageMapper.selectConversations(111,0,20);
        for (Message message: list){
            System.out.println(message);
        }

        System.out.println(messageMapper.countById(111));

        list = messageMapper.selectLetters("111_112",0,10);
        for (Message message: list){
            System.out.println(message);
        }

        System.out.println(messageMapper.selectLetterCount("111_112"));

        System.out.println(messageMapper.selectLetterUnreadCount(131,"111_131"));
    }

}
