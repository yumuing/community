package top.yumuing.community.service;

import top.yumuing.community.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author yumuuing
* @description 针对表【message】的数据库操作Service
* @createDate 2023-02-08 00:47:08
*/
public interface MessageService extends IService<Message> {
    public List<Message> findConversations(int userId, int offset, int limit);

    public int findConversationCount(int userId);

    public List<Message> findLetters(String conversationId, int offset, int limit);

    public int findLetterCount(String conversationId);

    public  int findLetterUnreadCount(int userId, String conversationId);

    public int addMessage(Message message);

    public int readMessage(List<Integer> idList);
}
