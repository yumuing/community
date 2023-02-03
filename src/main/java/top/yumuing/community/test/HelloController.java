package top.yumuing.community.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.Path;

@Controller
@RequestMapping("/yumuing")
public class HelloController {
    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @Autowired
    public TestService testService;

    @RequestMapping("/dateOne")
    @ResponseBody
    public String oneDate() {
        return testService.getDateOne();
    }

    @RequestMapping("/dateTwo")
    @ResponseBody
    public String twoDate() {
        return testService.getDateTwo();
    }

// Get 请求
//  yumuing/params?username=yumuing&password=1234 返回hello
    @ResponseBody
    @RequestMapping(path = {"/params"},
            method = {RequestMethod.GET})
    public String params(@RequestParam(name = "username", required = false,defaultValue = "test") String username,
                         @RequestParam(name = "password", required = false,defaultValue = "test") String password) {
        if (username.equals("yumuing") && password.equals("1234")) {
            return "hello!";
        } else {
            return "bye!";
        }
    }

//    yumuing/user/{userId}
    @ResponseBody
    @RequestMapping(path = "/user/{userId}",method = {RequestMethod.GET})
    public int userId(@PathVariable("userId") int userId){
        return userId;
    }

//    Post 请求
    @RequestMapping(path = "/user",method = {RequestMethod.POST})
    @ResponseBody
    public String user(@RequestParam(name = "name",required = false,defaultValue = "name") String name,
                       @RequestParam(name = "age",required = false,defaultValue = "age")String age){
        return name+": "+age;
    }

//    响应 HTML 数据
    @RequestMapping(path = "/test",method = RequestMethod.GET)
    public ModelAndView test(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","30");
        mav.addObject("age","hdciuwhe");
        mav.setViewName("demo/view");
        return mav;
    }
}




