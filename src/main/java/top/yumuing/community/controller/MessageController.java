package top.yumuing.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.yumuing.community.annotation.LoginRequired;
import top.yumuing.community.entity.Message;
import top.yumuing.community.entity.Page;
import top.yumuing.community.entity.User;
import top.yumuing.community.service.MessageService;
import top.yumuing.community.service.UserService;
import top.yumuing.community.util.HostHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author yumuing
 */

@Controller
@RequestMapping("/letter")
public class MessageController {

    @Autowired
    @Qualifier("messageServiceImpl")
    private MessageService messageServiceImpl;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userServiceImpl;

    @Autowired
    private HostHolder hostHolder;

    /**
     * 私信列表
     * 私信：用户之间，单个会话
     * 会话：当前用户与多个用户的私信列表
     */
    @LoginRequired
    @RequestMapping(path = "/list",method = RequestMethod.GET)
    public String getLetterList(Model model, Page page){
        User user = hostHolder.getUser();
        // 分页信息
        page.setLimit(5);
        page.setPath("/letter/list");
        page.setRows(messageServiceImpl.findConversationCount(user.getId()));

        // 会话列表
        List<Message> conversationList = messageServiceImpl.findConversations(user.getId(),page.getOffset(),page.getLimit());

        // 存入 map 中
        List<Map<String,Object>> conversations = new ArrayList<>();

        if (conversationList != null){
            for (Message message: conversationList){
                Map<String, Object> map = new HashMap<>();
                map.put("conversation",message);
                // 私信数量
                map.put("letterCount",messageServiceImpl.findLetterCount(message.getConversationId()));
                // 私信未读消息数量
                map.put("unreadCount",messageServiceImpl.findLetterUnreadCount(user.getId(),message.getConversationId()));
                int targetId = user.getId().equals(message.getFromId()) ? message.getToId() : message.getFromId();
                // 查询目标用户
                map.put("target",userServiceImpl.findUserById(targetId));
                conversations.add(map);
            }
        }

        model.addAttribute("conversations",conversations);

        // 查询会话未读消息数量
        int letterUnreadCount = messageServiceImpl.findLetterUnreadCount(user.getId(),null);

        model.addAttribute("letterUnreadCount",letterUnreadCount);

        return "/site/letter";

    }

    @RequestMapping(path = "/detail/{conversationId}",method = RequestMethod.GET)
    public String getLetterDetail(@PathVariable("conversationId") String conversationId, Page page,Model model){
        // 分页信息
        page.setLimit(5);
        page.setPath("/letter/detail/" + conversationId);
        page.setRows(messageServiceImpl.findLetterCount(conversationId));

        // 私信详情列表
        List<Message> letterList = messageServiceImpl.findLetters(conversationId,page.getOffset(),page.getLimit());
        List<Map<String,Object>> letters = new ArrayList<>();

        if (letterList != null){
            for(Message message: letterList){
                Map<String,Object> map = new HashMap<>();
                map.put("letter",message);
                map.put("fromUser",userServiceImpl.findUserById(message.getFromId()));
                letters.add(map);
            }
        }

        model.addAttribute("letters",letters);

        // 查询私信目标
        model.addAttribute("target",getLetterTarget(conversationId));

        return "/site/letter-detail";
    }


    // 查询私信目标
    private User getLetterTarget(String conversationId){
        String[] ids = conversationId.split("_");
        int id1 = Integer.parseInt(ids[0]);
        int id2 = Integer.parseInt(ids[1]);

        if (hostHolder.getUser().getId() == id1){
            return userServiceImpl.findUserById(id2);
        }else {
            return userServiceImpl.findUserById(id1);
        }
    }
}
