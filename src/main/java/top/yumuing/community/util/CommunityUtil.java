package top.yumuing.community.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

public class CommunityUtil {
    // 生成随机字符串
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    // MD5
    // 密码都加上随机字符串，再进行 MD5 加密，提高安全性
    // key：原始密码
    public static String md5(String key){
        if (StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes(StandardCharsets.UTF_8));
    }

    public static String getJsonString(int code, String msg, Map<String, Object> map){
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode json = objectMapper.createObjectNode();
        json.put("code", code);
        json.put("msg", msg);
        if (map != null){
            for (String key: map.keySet()){
                json.put(key, String.valueOf(map.get(key)));
            }
        }
        return json.toString();
    }

    public static String getJsonString(int code, String msg){
        return getJsonString(code, msg, null);
    }

    public static String getJsonString(int code){
        return getJsonString(code, null,null);
    }
}
