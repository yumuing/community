package top.yumuing.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.yumuing.community.entity.LoginTicket;
import top.yumuing.community.service.LoginTicketService;
import top.yumuing.community.mapper.LoginTicketMapper;
import org.springframework.stereotype.Service;

/**
* @author yumuuing
* @description 针对表【login_ticket】的数据库操作Service实现
* @createDate 2023-02-08 00:47:08
*/
@Service
public class LoginTicketServiceImpl extends ServiceImpl<LoginTicketMapper, LoginTicket>
    implements LoginTicketService{

}




