package top.yumuing.community.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.yumuing.community.entity.Comment;
import top.yumuing.community.entity.DiscussPost;
import top.yumuing.community.entity.Page;
import top.yumuing.community.entity.User;
import top.yumuing.community.service.CommentService;
import top.yumuing.community.service.DiscussPostService;
import top.yumuing.community.service.UserService;
import top.yumuing.community.util.CommunityConstant;
import top.yumuing.community.util.CommunityUtil;
import top.yumuing.community.util.HostHolder;

import java.util.*;


/**
 * @author yumuing
 */
@Controller
@RequestMapping("/discuss")
public class DiscussPostController implements CommunityConstant{

    @Autowired
    @Qualifier("discussPostServiceImpl")
    private DiscussPostService discussPostServiceImpl;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userServiceImpl;

    @Autowired
    @Qualifier("commentServiceImpl")
    private CommentService commentServiceImpl;


    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = "/add",  method = RequestMethod.POST)
    @ResponseBody
    public String addDiscussPost(String title, String content){
        User user = hostHolder.getUser();

        if (user == null){
            return CommunityUtil.getJsonString(403, "你还没有登录！");
        }

        // 插入数据
        DiscussPost discussPost = new DiscussPost();
        discussPost.setUserId(String.valueOf(user.getId()));
        discussPost.setTitle(title);
        discussPost.setContent(content);
        discussPost.setCreateTime(new Date());
        System.out.println(discussPost);
        discussPostServiceImpl.addDiscussPost(discussPost);

        // 后面统一处理异常
        return CommunityUtil.getJsonString(200,"发布成功！");

    }

    @RequestMapping(path = "/detail/{discussPostId}",method = RequestMethod.GET)
    public String getDisPost(@PathVariable("discussPostId") int discussPostId, Model model, Page page){
        // 查询帖子
        DiscussPost discussPost = discussPostServiceImpl.findDiscussPostById(discussPostId);
        model.addAttribute("post",discussPost);

        // 查询用户，不用关联查询，耦合高
        User user = userServiceImpl.findUserById(Integer.parseInt(discussPost.getUserId()));
        model.addAttribute("user",user);

        // 评论分页信息
        page.setLimit(5);
        page.setPath("/discuss/detail/" + discussPostId);
        page.setRows(discussPost.getCommentCount());

        // 帖子的评论：评论
        // 评论列表
        List<Comment> comments = commentServiceImpl.findComments(ENTITY_TYPE_POST,discussPost.getId(),page.getOffset(),page.getLimit());
        // 评论值列表：显示对象列表
        List<Map<String,Object>> commentVoList = new ArrayList<>();
        if(comments != null){
            for (Comment comment : comments){
                // 一个评论的 vo
                Map<String,Object> commentVo = new HashMap<>();
                // 添加一个评论
                commentVo.put("comment",comment);
                // 评论作者
                commentVo.put("user",userServiceImpl.findUserById(comment.getUserId()));

                // 帖子本身就是评论目标，为0

                // 回复：评论的评论
                List<Comment> replyList = commentServiceImpl.findComments(ENTITY_TYPE_COMMENT,comment.getId(), 0, Integer.MAX_VALUE);
                // 回复的 vo 列表
                List<Map<String,Object>> replyVoList = new ArrayList<>();
                if (replyList != null){
                    for(Comment reply: replyList){
                        Map<String,Object> replyVo = new HashMap<>();
                        // 存入reply
                        replyVo.put("reply",reply);

                        // 存入作者
                        replyVo.put("user",userServiceImpl.findUserById(reply.getUserId()));

                        // 回复的目标：targetId
                        User target = reply.getTargetId() == 0 ? null : userServiceImpl.findUserById(reply.getTargetId());
                        replyVo.put("target",target);

                        replyVoList.add(replyVo);
                    }
                }
                commentVo.put("replyVoList",replyVoList);

                // 回复数量
                int replyCount = commentServiceImpl.findCommentCount(ENTITY_TYPE_COMMENT,comment.getId());
                commentVo.put("replyCount",replyCount);

                commentVoList.add(commentVo);
            }
        }

        model.addAttribute("comments", commentVoList);

        return "/site/discuss-detail";
    }
}
