package top.yumuing.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.HtmlUtils;
import top.yumuing.community.entity.Message;
import top.yumuing.community.service.MessageService;
import top.yumuing.community.mapper.MessageMapper;
import org.springframework.stereotype.Service;
import top.yumuing.community.util.SensitiveWordUtil;

import java.util.List;

/**
* @author yumuuing
* @description 针对表【message】的数据库操作Service实现
* @createDate 2023-02-08 00:47:08
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService{

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private SensitiveWordUtil sensitiveWordUtil;

    @Override
    public List<Message> findConversations(int userId, int offset, int limit) {
        return messageMapper.selectConversations(userId,offset,limit);
    }

    @Override
    public int findConversationCount(int userId) {
        return messageMapper.countById(userId);
    }

    @Override
    public List<Message> findLetters(String conversationId, int offset, int limit) {
        return messageMapper.selectLetters(conversationId,offset,limit);
    }

    @Override
    public int findLetterCount(String conversationId) {
        return messageMapper.selectLetterCount(conversationId);
    }

    @Override
    public int findLetterUnreadCount(int userId, String conversationId) {
        return messageMapper.selectLetterUnreadCount(userId,conversationId);
    }

    @Override
    public int addMessage(Message message) {
        message.setContent(sensitiveWordUtil.replace(HtmlUtils.htmlEscape(message.getContent())));
        return messageMapper.insertAll(message);
    }

    @Override
    public int readMessage(List<Integer> idList) {
        return messageMapper.updateStatusByIds(idList,1);
    }
}




