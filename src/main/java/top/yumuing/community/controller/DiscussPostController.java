package top.yumuing.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.yumuing.community.entity.DiscussPost;
import top.yumuing.community.entity.User;
import top.yumuing.community.service.DiscussPostService;
import top.yumuing.community.service.UserService;
import top.yumuing.community.util.CommunityUtil;
import top.yumuing.community.util.HostHolder;

import java.util.Date;

@Controller
@RequestMapping("/discuss")
public class DiscussPostController {

    @Autowired
    @Qualifier("discussPostServiceImpl")
    private DiscussPostService discussPostServiceImpl;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userServiceImpl;


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
    public String getDisPost(@PathVariable("discussPostId") int discussPostId, Model model){
        // 查询帖子
        DiscussPost discussPost = discussPostServiceImpl.findDiscussPostById(discussPostId);
        model.addAttribute("post",discussPost);

        // 查询用户，不用关联查询，耦合高
        User user = userServiceImpl.findUserById(Integer.parseInt(discussPost.getUserId()));
        model.addAttribute("user",user);

        // 回复等后续

        return "/site/discuss-detail";
    }
}
