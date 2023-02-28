package top.yumuing.community.service;

import org.springframework.stereotype.Service;
import top.yumuing.community.entity.DiscussPost;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;

/**
* @author yumuuing
* @description 针对表【discuss_post】的数据库操作Service
* @createDate 2023-02-08 00:47:08
*/
@Service
public interface DiscussPostService extends IService<DiscussPost> {
    public List<DiscussPost> findDiscussPosts(int userId,int offset,int limit);

    public int findDiscussPostsRows(int userId);
}
