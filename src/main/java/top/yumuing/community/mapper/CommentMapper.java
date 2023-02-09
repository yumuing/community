package top.yumuing.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.yumuing.community.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author yumuuing
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2023-02-08 00:47:09
* @Entity top.yumuing.community.entity.Comment
*/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}




