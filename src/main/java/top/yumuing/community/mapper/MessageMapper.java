package top.yumuing.community.mapper;
import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.annotations.Mapper;
import top.yumuing.community.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author yumuuing
* @description 针对表【message】的数据库操作Mapper
* @createDate 2023-02-08 00:47:08
* @Entity top.yumuing.community.entity.Message
*/
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    /**
     * 查询当前用户的会话列表
     * 针对每个会话只返回一条最新的私信
     */

    List<Message> selectConversations(int userId, int offset, int limit);

    /**
     * 查询当前用户会话数量
     */

    int countById(@Param("userId") int userId);

    /**
     * 查询某个会话所包含的私信列表
     */
    List<Message> selectLetters(String conversationId, int offset, int limit);

    /**
     * 查询某个会话包含的私信数量
     */
    int selectLetterCount(String conversationId);

    /**
     * 查询未读私信数量
     */

    int selectLetterUnreadCount(int userId, String conversationId);

}




