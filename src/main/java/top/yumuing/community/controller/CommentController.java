package top.yumuing.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.yumuing.community.annotation.LoginRequired;
import top.yumuing.community.entity.Comment;
import top.yumuing.community.service.CommentService;
import top.yumuing.community.util.HostHolder;

import java.util.Date;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    @Qualifier("commentServiceImpl")
    private CommentService commentServiceImpl;

    @Autowired
    private HostHolder hostHolder;

    // 增加评论
    @LoginRequired
    @RequestMapping(path = "/add/{discussPostId}",method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId") int discussPostId, Comment comment){
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentServiceImpl.addComment(comment);

        return "redirect:/discuss/detail/" + discussPostId;
    }

}
