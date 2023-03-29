package top.yumuing.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import top.yumuing.community.entity.Comment;
import top.yumuing.community.service.CommentService;
import top.yumuing.community.mapper.CommentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author yumuuing
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2023-02-08 00:47:09
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    @Autowired
    private CommentMapper commentMapper;

    public List<Comment> findComments(int entityType, int entityId, int offset, int limit){
        return commentMapper.selectCommentsByEntity(entityType,entityId,offset,limit);
    }

    public int findCommentCount(int entityType, int entityId){
        return commentMapper.countByEntityIdAndEntityType(entityId,entityType);
    }

}




