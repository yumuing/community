package top.yumuing.community;

import com.github.houbb.heaven.util.lang.CharUtil;
import com.github.houbb.sensitive.word.api.ISensitiveWordReplace;
import com.github.houbb.sensitive.word.api.ISensitiveWordReplaceContext;
import com.github.houbb.sensitive.word.api.IWordAllow;
import com.github.houbb.sensitive.word.api.IWordDeny;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.github.houbb.sensitive.word.core.SensitiveWordHelper;
import com.github.houbb.sensitive.word.support.allow.WordAllows;
import com.github.houbb.sensitive.word.support.deny.WordDenys;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import top.yumuing.community.config.SensitiveWordsConfig;
import top.yumuing.community.util.SensitiveWordUtil;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveWordTest {
    private static final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    // 自定义替换规则
    // 五星红旗替换成国家旗帜
    // 毛主席替换成教员
    // 其他默认 *
    public class MySensitiveWordReplace implements ISensitiveWordReplace {

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
    public class MyWordDeny implements IWordDeny {

        @Override
        public List<String> deny() {
            List<String> list = new ArrayList<String>();;
            try {
                Resource mySensitiveWords = new ClassPathResource("mySensitiveWords.txt");
                Path mySensitiveWordsPath = Paths.get(mySensitiveWords.getFile().getPath());
                list =  Files.readAllLines(mySensitiveWordsPath, StandardCharsets.UTF_8);
//                list.addAll(mySensitiveWordsLine);

            } catch (IOException ioException) {
                logger.error("读取文件错误！"+ ioException.getMessage());
            }
            return list;
        }

    }

    // 自定义非敏感词
    public class MyWordAllow implements IWordAllow {

        @Override
        public List<String> allow() {
            List<String> list = new ArrayList<String>();;
            try {
                Resource myAllowWords = new ClassPathResource("myAllowWords.txt");
                Path myAllowWordsPath = Paths.get(myAllowWords.getFile().getPath());
                list =  Files.readAllLines(myAllowWordsPath, StandardCharsets.UTF_8);
            } catch (IOException ioException) {
                logger.error("读取文件错误！"+ ioException.getMessage());
            }
            return list;
        }

    }

    @Test
    public void Test02(){
        final String text = "五星红旗迎风飘扬，毛主席的画像屹立在广场前。";

        ISensitiveWordReplace replace = new MySensitiveWordReplace();
        String result = SensitiveWordHelper.replace(text, replace);

        System.out.println(result);
    }

    @Test
    public void Test01(){

        final String text = "五星红旗迎风飘扬，毛主席的画像屹立在天安门前。";

        System.out.println(SensitiveWordHelper.contains(text));
        // 返回所有敏感词
        List<String> wordList = SensitiveWordHelper.findAll(text);
        System.out.println(wordList);
        // 返回替换后的字符
        String result = SensitiveWordHelper.replace(text);
        System.out.println(result);
        // 修改替换字符为 # 默认为*
        result = SensitiveWordHelper.replace(text, '#');
        System.out.println(result);

    }



    @Test
    public void Test03(){
        String result;

        // 忽略大小写
        final String text1 = "fuCK the bad words.";
        System.out.println(text1);
        List<String> wordList1 = SensitiveWordHelper.findAll(text1);
        System.out.println(wordList1);
        result = SensitiveWordHelper.replace(text1);
        System.out.println(result);

        // 忽略半角圆角
        final String text2 = "f u C K the bad words.";
        System.out.println(text2);
        List<String> wordList2 = SensitiveWordHelper.findAll(text2);
        System.out.println(wordList1);
        result = SensitiveWordHelper.replace(text2);
        System.out.println(result);

        // 忽略数字常见形式转换
        final String text3 = "这个是我的微信：9⓿二肆⁹₈③⑸⒋➃㈤㊄";
        System.out.println(text3);
        List<String> wordList3 = SensitiveWordHelper.findAll(text3);
        System.out.println(wordList3);
        result = SensitiveWordHelper.replace(text3);
        System.out.println(result);

        // 忽略简繁体
        final String text4 = "我爱我的祖国和五星紅旗。";
        System.out.println(text4);
        List<String> wordList4 = SensitiveWordHelper.findAll(text4);
        System.out.println(wordList4);
        result = SensitiveWordHelper.replace(text4);
        System.out.println(result);

        // 忽视英文书写格式
        final String text5 = "Ⓕⓤc⒦ the bad words";
        System.out.println(text5);
        List<String> wordList5 = SensitiveWordHelper.findAll(text5);
        System.out.println(wordList5);
        result = SensitiveWordHelper.replace(text5);
        System.out.println(result);

        // 配置自定义敏感词+系统敏感词
        IWordDeny wordDeny = WordDenys.chains(WordDenys.system(), new MyWordDeny());
        // 配置系统非敏感词 + 自定义非敏感词
        IWordAllow wordAllow = WordAllows.chains(WordAllows.system(), new MyWordAllow());
        SensitiveWordBs wordBs = SensitiveWordBs.newInstance()
                .wordDeny(wordDeny)
                .wordAllow(wordAllow)
                .init();

        // 忽略重复词
        // ignoreRepeat：是否忽略重复词，是为true
        final String text6 = "法网恢恢 哇 nnd 毛爷爷复活";
        System.out.println(text6);
        List<String> wordList6 = SensitiveWordBs.newInstance()
                .ignoreRepeat(false)
                .findAll(text6);
        System.out.println(wordList6);
        result = SensitiveWordHelper.replace(text6);
        System.out.println(result);

        System.out.println(wordBs.findAll(text6).toString());
        System.out.println(wordBs.replace(text6));

        // 邮箱检测
        final String text7 = "楼主好人，邮箱 sensitiveword@xx.com";
        System.out.println(text7);
        List<String> wordList7 = SensitiveWordHelper.findAll(text7);
        System.out.println(wordList7);
        result = SensitiveWordHelper.replace(text7);
        System.out.println(result);

        // 连续数字检测：手机号、qq号检测
        final String text8 = "你懂得：12345678";
        System.out.println(text8);
        // 默认检测 8 位
        List<String> wordList8 = SensitiveWordBs.newInstance().findAll(text8);
        System.out.println(wordList8);
        result = SensitiveWordHelper.replace(text8);
        System.out.println(result);

        // 指定数字的长度，避免误杀
        List<String> wordList9 = SensitiveWordBs.newInstance()
                .numCheckLen(9)
                .findAll(text8);
        System.out.println(wordList9);
        result = SensitiveWordHelper.replace(text8);
        System.out.println(result);

    }

    @Test
    public void Test05(){
        MyWordDeny myWordDeny = new MyWordDeny();
        List<String> list = myWordDeny.deny();
        System.out.println(list);
    }


    @Test
    public void reader(){

        Resource mySensitiveWordsPath = new ClassPathResource("mySensitiveWords.txt");


        try {
            Path path = Paths.get(mySensitiveWordsPath.getFile().getPath());
            String data =  Files.readString(path);
            List<String> arrays = Arrays.asList(data);
            System.out.println(arrays);

        } catch (IOException e) {
            logger.error("读取文件错误！"+ e.getMessage());
        }

    }



}
