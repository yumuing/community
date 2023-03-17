package top.yumuing.community.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import top.yumuing.community.annotation.LoginRequired;
import top.yumuing.community.entity.User;
import top.yumuing.community.service.UserService;
import top.yumuing.community.util.CommunityUtil;
import top.yumuing.community.util.HostHolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.domain.path}")
    private String domain;

    @Value("${community.upload.path}")
    private String upload;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userServiceImpl;

    @Autowired
    private HostHolder hostHolder;

    //返回账号设置页面
    @LoginRequired
    @RequestMapping(path = "/setting",method = RequestMethod.GET)
    public String getSettingPage(){
        return "/site/setting";
    }

    @LoginRequired
    @RequestMapping(path = "/upload",method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model){
        if (headerImage == null){
            model.addAttribute("imageError","您还没有上传图片！");
            return "/site/setting";
        }

        // 获取上传的文件原始名称
        String filename = headerImage.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));

        if (StringUtils.isBlank(suffix)){
            model.addAttribute("imageError","图片格式不正确！");
            return "/site/setting";
        }
        // 生成随机名称，防止覆盖
        filename = CommunityUtil.generateUUID() + suffix;
        // 确定文件路径
        File file = new File(upload+"/"+filename);
        try {
            headerImage.transferTo(file);
        } catch (IOException e) {
            logger.error("上传文件失败！"+e.getMessage());
            throw new RuntimeException("上传文件失败，服务器异常！"+e.getMessage());
        }

        // 更新用户头像路径（web路径）
        // 路径：域名/user/header/***.suffix
        User user = hostHolder.getUser();
        String headerUrl = domain + "/user/headerUrl/" + filename;
        userServiceImpl.updateHeaderUrl(user.getId(),headerUrl);

        return "redirect:/index";
    }

    // 获取头像
    @RequestMapping(path = "/headerUrl/{filename}",method = RequestMethod.GET)
    public void getHeader(@PathVariable("filename") String filename, HttpServletResponse response){
        // 服务器路径
        String filenamePath =  upload + "/" + filename;
        // 文件后缀
        String suffix = filenamePath.substring(filename.lastIndexOf("."));
        // 响应图片
        response.setContentType("image/" + suffix);
        try(
                OutputStream outputStream = response.getOutputStream();
                FileInputStream fileInputStream = new FileInputStream(filenamePath);
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fileInputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败："+ e.getMessage());
        }
    }

    // 修改密码
    @LoginRequired
    @RequestMapping(path = "/updatePassword", method = RequestMethod.POST)
    public String updatePassword(Model model, String newPassword, String oldPassword, String confirmPassword){
        if (!newPassword.equals(confirmPassword)){
            model.addAttribute("newPasswordError","两次填写的新密码不一致");
            return "/site/setting";
        }

        if (newPassword.length() <= 8){
            model.addAttribute("newPasswordError","新密码长度不能小于8位!");
            return "/site/setting";
        }

        if (oldPassword.equals(newPassword)){
            model.addAttribute("newPasswordError","新旧密码相同！请重新输入！");
            return "/site/setting";
        }
        User user = hostHolder.getUser();
        // 比较原密码是否正确
        if (StringUtils.isBlank(oldPassword) || !user.getPassword().equals(CommunityUtil.md5(oldPassword + user.getSalt()))){
            model.addAttribute("oldPasswordError","您输入的原密码错误！");
            return "/site/setting";
        }
        // 更新密码
        String salt = CommunityUtil.generateUUID().substring(0,5);
        userServiceImpl.updatePassword(user.getId(),CommunityUtil.md5(newPassword+ salt));
        userServiceImpl.updateSalt(user.getId(),salt);
        model.addAttribute("msg","您的密码已修改完成，可用新密码登录！");
        model.addAttribute("target","/index");
        model.addAttribute("url",domain+"/index");
        return "/site/operate-result";
    }
}
