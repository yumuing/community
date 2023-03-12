package top.yumuing.community.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {

    @Bean
    public Producer kaptchaProduce(){
        Properties properties=new Properties();
        //图片的宽度
        properties.setProperty("kaptcha.image.width","100");
        //图片的高度
        properties.setProperty("kaptcha.image.height","40");
        //字体大小
        properties.setProperty("kaptcha.textproducer.font.size","32");
        //字体颜色（RGB）
        properties.setProperty("kaptcha.textproducer.font.color","0,0,0");
        //验证码字符的集合
        properties.setProperty("kaptcha.textproducer.char.string","123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        //验证码长度（即在上面集合中随机选取几位作为验证码）
        properties.setProperty("kaptcha.textproducer.char.length","4");
        //图片的干扰样式：默认存在无规则划线干扰
        //无干扰：com.google.code.kaptcha.impl.NoNoise
//        properties.setProperty("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");
        //图片干扰颜色：默认为黑色
        properties.setProperty("kaptcha.noise.color", "black");
        //图片渲染效果：默认水纹
        // 水纹com.google.code.kaptcha.impl.WaterRipple 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy 阴影com.google.code.kaptcha.impl.ShadowGimpy
        properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");

        DefaultKaptcha Kaptcha = new DefaultKaptcha();
        Config config=new Config(properties);
        Kaptcha.setConfig(config);
        return Kaptcha;
    }


}
