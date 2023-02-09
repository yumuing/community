package top.yumuing.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.yumuing.community.entity.DiscussPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author yumuuing
* @description 针对表【discuss_post】的数据库操作Mapper
* @createDate 2023-02-08 00:47:08
* @Entity top.yumuing.community.entity.DiscussPost
*/
@Mapper
public interface DiscussPostMapper extends BaseMapper<DiscussPost> {

    // 分页查询帖子，为了与用户查询个人帖子功能复用，
    // userId：首页默认为 0，而需要查询用户的帖子就填入对应 userId
    // offset：每一页起始行行号
    // limit：最多显示多少条数据
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    // 查询一共多少页数据
    // userId：首页默认为 0，而需要查询用户的帖子就填入对应 userId
    // @Param("参数别名")：书写参数别名
    // 需要动态拼接 SQL 语句时，传入的参数有且只有一个，参数之前必须增加 @Param("参数别名")，否则会报错。
    int selectDiscussPostRows(@Param("userId") int userId);

}




