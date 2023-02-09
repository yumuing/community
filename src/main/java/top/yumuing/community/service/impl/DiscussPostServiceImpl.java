package top.yumuing.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import top.yumuing.community.entity.DiscussPost;
import top.yumuing.community.service.DiscussPostService;
import top.yumuing.community.mapper.DiscussPostMapper;
import org.springframework.stereotype.Service;

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
    public DiscussPostMapper discussPostMapper;

    @Override
    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId,offset,limit);
    }

    @Override
    public int findDiscussPostsRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}




