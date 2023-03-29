package top.yumuing.community.util;

public interface CommunityConstant {
    // 激活成功
    int ACTIVATION_SUCCESS = 0;

    // 重复激活
    int ACTIVATION_REPEAT = 1;

    // 激活失败

    int ACTIVATION_FAILURE = 2;

    // 激活用户不存在
    int ACTIVATION_NULL = 3;

    // 默认登录状态超时常量
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    // 记住状态的登录凭证超时时间
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;

    // 实体类型：帖子
    int ENTITY_TYPE_POST = 1;

    // 实体类型：评论
    int ENTITY_TYPE_COMMENT = 2;
}
