package top.yumuing.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.util.HtmlUtils;
import top.yumuing.community.entity.DiscussPost;
import top.yumuing.community.service.CommentService;
import top.yumuing.community.service.DiscussPostService;
import top.yumuing.community.mapper.DiscussPostMapper;
import org.springframework.stereotype.Service;
import top.yumuing.community.util.SensitiveWordUtil;

import java.util.List;

/**
* @author yumuuing
* @description 针对表【discuss_post】的数据库操作Service实现
* @createDate 2023-02-08 00:47:08
*/
@Service
public class DiscussPostServiceImpl extends ServiceImpl<DiscussPostMapper, DiscussPost>
    implements DiscussPostService{
    @Resource
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SensitiveWordUtil sensitiveWordUtil;



    @Override
    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId,offset,limit);
    }

    @Override
    public int findDiscussPostsRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }

    @Override
    public int addDiscussPost(DiscussPost discussPost) {
        if (discussPost == null){
            throw new IllegalArgumentException("参数不能为空！");
        }
        // 转义 html 标记，并过滤敏感词
        String title = sensitiveWordUtil.replace(HtmlUtils.htmlEscape(discussPost.getTitle()));
        String content = sensitiveWordUtil.replace(HtmlUtils.htmlEscape(discussPost.getContent()));
        // 存入对象中
        discussPost.setTitle(title);
        discussPost.setContent(content);
        discussPost.setType(0);
        discussPost.setStatus(0);

        // 插入数据
        return discussPostMapper.insertAll(discussPost);
    }

    @Override
    public DiscussPost findDiscussPostById(int id) {
        return discussPostMapper.selectOneById(id);
    }
}




