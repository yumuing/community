package top.yumuing.community.config;

import com.github.houbb.heaven.util.lang.CharUtil;
import com.github.houbb.sensitive.word.api.ISensitiveWordReplace;
import com.github.houbb.sensitive.word.api.ISensitiveWordReplaceContext;
import com.github.houbb.sensitive.word.api.IWordAllow;
import com.github.houbb.sensitive.word.api.IWordDeny;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.github.houbb.sensitive.word.support.allow.WordAllows;
import com.github.houbb.sensitive.word.support.deny.WordDenys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SensitiveWordsConfig {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveWordsConfig.class);

    // 自定义替换规则
    // 五星红旗替换成国家旗帜
    // 毛主席替换成教员
    // 其他默认 *
    public class mySensitiveWordReplace implements ISensitiveWordReplace {

        @Override
        public String replace(ISensitiveWordReplaceContext context) {
            String sensitiveWord = context.sensitiveWord();
            // 自定义不同的敏感词替换策略，可以从数据库等地方读取
            if("五星红旗".equals(sensitiveWord)) {
                return "国家旗帜";
            }
            if("毛主席".equals(sensitiveWord)) {
                return "教员";
            }

            // 其他默认使用 * 代替
            int wordLength = context.wordLength();
            return CharUtil.repeat('*', wordLength);
        }

    }

    // 自定义敏感词
    // 注意每一行为一个敏感词，单行不能只包括空格，否则，也会把空格识别为敏感词
    public class MyWordDeny implements IWordDeny {

        @Override
        public List<String> deny() {
            List<String> list = new ArrayList<String>();;
            try {
                Resource mySensitiveWords = new ClassPathResource("mySensitiveWords.txt");
                Path mySensitiveWordsPath = Paths.get(mySensitiveWords.getFile().getPath());
                list =  Files.readAllLines(mySensitiveWordsPath, StandardCharsets.UTF_8);
            } catch (IOException ioException) {
                logger.error("读取敏感词文件错误！"+ ioException.getMessage());
            }
            return list;
        }

    }

    // 自定义非敏感词
    // 注意每一行为一个非敏感词，单行不能只包括空格，否则，也会把空格识别为非敏感词
    public class MyWordAllow implements IWordAllow {

        @Override
        public List<String> allow() {
            List<String> list = new ArrayList<String>();;
            try {
                Resource myAllowWords = new ClassPathResource("myAllowWords.txt");
                Path myAllowWordsPath = Paths.get(myAllowWords.getFile().getPath());
                list =  Files.readAllLines(myAllowWordsPath, StandardCharsets.UTF_8);
            } catch (IOException ioException) {
                logger.error("读取非敏感词文件错误！"+ ioException.getMessage());
            }
            return list;
        }

    }

    // 配置系统敏感词 + 自定义敏感词
    IWordDeny wordDeny = WordDenys.chains(WordDenys.system(), new MyWordDeny());
    // 配置系统非敏感词 + 自定义非敏感词
    IWordAllow wordAllow = WordAllows.chains(WordAllows.system(), new MyWordAllow());

    @Bean
    public SensitiveWordBs sensitiveWordBs(){
        return SensitiveWordBs.newInstance()
                // 忽略大小写
                .ignoreCase(true)
                // 忽略半角圆角
                .ignoreWidth(true)
                // 忽略数字的写法
                .ignoreNumStyle(true)
                // 忽略中文的书写格式：简繁体
                .ignoreChineseStyle(true)
                // 忽略英文的书写格式
                .ignoreEnglishStyle(true)
                // 忽略重复词
                .ignoreRepeat(false)
                // 是否启用数字检测
                .enableNumCheck(true)
                // 是否启用邮箱检测
                .enableEmailCheck(true)
                // 是否启用链接检测
                .enableUrlCheck(true)
                // 数字检测，自定义指定长度
                .numCheckLen(8)
                // 配置自定义敏感词
                .wordDeny(wordDeny)
                // 配置非自定义敏感词
                .wordAllow(wordAllow)
                .init();
    }

}
