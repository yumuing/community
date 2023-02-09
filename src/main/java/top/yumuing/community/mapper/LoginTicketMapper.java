package top.yumuing.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.yumuing.community.entity.LoginTicket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author yumuuing
* @description 针对表【login_ticket】的数据库操作Mapper
* @createDate 2023-02-08 00:47:08
* @Entity top.yumuing.community.entity.LoginTicket
*/
@Mapper
public interface LoginTicketMapper extends BaseMapper<LoginTicket> {

}




