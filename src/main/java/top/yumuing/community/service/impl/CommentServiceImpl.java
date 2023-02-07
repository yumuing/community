package top.yumuing.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.yumuing.community.entity.Comment;
import top.yumuing.community.service.CommentService;
import top.yumuing.community.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author yuzhe
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2023-02-08 00:47:09
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

}




