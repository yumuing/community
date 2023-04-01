package top.yumuing.community.service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.yumuing.community.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author yumuuing
* @description 针对表【comment】的数据库操作Service
* @createDate 2023-02-08 00:47:09
*/
public interface CommentService extends IService<Comment> {

    public List<Comment> findComments(int entityType, int entityId, int offset, int limit);

    public int findCommentCount(int entityType, int entityId);

    // 增加评论，记得自行添加相同注解
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int addComment(Comment comment);
}
