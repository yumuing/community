package top.yumuing.community.mapper;
import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import org.springframework.stereotype.Repository;
import top.yumuing.community.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author yuzhe
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-02-07 22:50:59
* @Entity top.yumuing.community.entity.User
*/
//@Mapper
//@Repository
public interface UserMapper extends BaseMapper<User> {
    List<User> selectAllByIdOrderByAge(@Param("id") Integer id);

    int insertAll(User user);

    int insertBatch(@Param("userCollection") Collection<User> userCollection);

    int insertSelective(User user);

}




