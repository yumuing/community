package top.yumuing.community.mapper;
import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.annotations.Mapper;
import top.yumuing.community.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author yumuuing
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2023-02-08 00:47:09
* @Entity top.yumuing.community.entity.Comment
*/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    // 分页
    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    int countByEntityIdAndEntityType(@Param("entityId") Integer entityId, @Param("entityType") Integer entityType);

    // 增加评论
    int insertComment(Comment comment);
}




