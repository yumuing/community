package top.yumuing.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.yumuing.community.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author yumuuing
* @description 针对表【message】的数据库操作Mapper
* @createDate 2023-02-08 00:47:08
* @Entity top.yumuing.community.entity.Message
*/
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}




