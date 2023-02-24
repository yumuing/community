package top.yumuing.community.controller;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.yumuing.community.entity.DiscussPost;
import top.yumuing.community.entity.Page;
import top.yumuing.community.entity.User;
import top.yumuing.community.service.DiscussPostService;
import top.yumuing.community.service.impl.DiscussPostServiceImpl;
import top.yumuing.community.service.impl.UserServiceImpl;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private DiscussPostServiceImpl discussPostServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @RequestMapping(path = "/index",method = RequestMethod.GET)
    public String getIndecPage(Model model ,Page page){

        //获取分页
        page.setRows(discussPostServiceImpl.findDiscussPostsRows(0));
        page.setPath("/index");

        // 获取帖子
        List<DiscussPost> discussPostList = discussPostServiceImpl.findDiscussPosts(0,page.getOffset(),page.getLimit());
        List<Map<String,Object>> discussPosts = new ArrayList<>();
        if (discussPostList != null){
            for(DiscussPost post: discussPostList){
                Map<String, Object> map = new HashMap<>();
                map.put("post",post);
                System.out.println(post.getUserId());
                User user = userServiceImpl.findUserById(Integer.parseInt(post.getUserId()));
                map.put("user",user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        return "/index";
    }
}
