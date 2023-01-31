package top.yumuing.community.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/yumuing")
public class HelloController {
    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello";
    }

    @Autowired
    public TestService testService;

    @RequestMapping("/dateOne")
    @ResponseBody
    public String oneDate(){
        return testService.getDateOne();
    }

    @RequestMapping("/dateTwo")
    @ResponseBody
    public String twoDate(){
        return testService.getDateTwo();
    }
}
