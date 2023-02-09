package top.yumuing.community.mapper;
import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.annotations.Mapper;
import top.yumuing.community.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author yumuuing
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-02-08 00:47:08
* @Entity top.yumuing.community.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User selectOneById(@Param("id") int id);
}




