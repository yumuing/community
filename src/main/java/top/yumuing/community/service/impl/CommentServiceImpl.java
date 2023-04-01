package top.yumuing.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;
import top.yumuing.community.annotation.LoginRequired;
import top.yumuing.community.entity.Comment;
import top.yumuing.community.service.CommentService;
import top.yumuing.community.mapper.CommentMapper;
import org.springframework.stereotype.Service;
import top.yumuing.community.service.DiscussPostService;
import top.yumuing.community.util.CommunityConstant;
import top.yumuing.community.util.SensitiveWordUtil;

import java.util.List;

/**
* @author yumuuing
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2023-02-08 00:47:09
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService, CommunityConstant {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SensitiveWordUtil sensitiveWordUtil;

    @Autowired
    @Qualifier("discussPostServiceImpl")
    private DiscussPostService discussPostServiceImpl;

    public List<Comment> findComments(int entityType, int entityId, int offset, int limit){
        return commentMapper.selectCommentsByEntity(entityType,entityId,offset,limit);
    }

    public int findCommentCount(int entityType, int entityId){
        return commentMapper.countByEntityIdAndEntityType(entityId,entityType);
    }


    // 增加评论，更新评论数量
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int addComment(Comment comment) {
        if (comment == null){
            throw new IllegalArgumentException("参数不能为空！");
        }
        // 过滤html标签和敏感词
        String filter = sensitiveWordUtil.replace(HtmlUtils.htmlEscape(comment.getContent()));

        // 存入对象
        comment.setContent(filter);
        System.out.println(comment);
        int rows = commentMapper.insertComment(comment);
        // 更新评论数量：帖子
        if (comment.getEntityType() == ENTITY_TYPE_POST){
            int count = commentMapper.countByEntityIdAndEntityType(comment.getEntityId(),comment.getEntityType());
            discussPostServiceImpl.updateCommentCount(comment.getEntityId(),count);
        }
        return rows;
    }

}




