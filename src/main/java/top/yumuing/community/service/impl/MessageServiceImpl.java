package top.yumuing.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.yumuing.community.entity.Message;
import top.yumuing.community.service.MessageService;
import top.yumuing.community.mapper.MessageMapper;
import org.springframework.stereotype.Service;

/**
* @author yumuuing
* @description 针对表【message】的数据库操作Service实现
* @createDate 2023-02-08 00:47:08
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService{

}




