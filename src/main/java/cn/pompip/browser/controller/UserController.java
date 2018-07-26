package cn.pompip.browser.controller;

import cn.pompip.browser.common.entity.Result;
import cn.pompip.browser.exception.ParamErrorException;
import cn.pompip.browser.exception.UnLoginException;
import cn.pompip.browser.model.UserBean;
import cn.pompip.browser.service.UserService;
import cn.pompip.browser.util.Cache.LocalCache;
import cn.pompip.browser.util.HttpUtil;
import cn.pompip.browser.util.PropertiesFileUtil;
import cn.pompip.browser.util.date.DateTimeUtil;
import cn.pompip.browser.util.security.MD5;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@RequestMapping("/user")
@Controller
public class UserController {


    @Autowired
    private UserService userService;


    /***
     * 手机号登录
     */
    @ResponseBody
    @RequestMapping(value = "/phoneNumLogin")
    public Result phoneNumLogin(HttpServletRequest request) throws Exception {
        Result result = new Result();
        String version = request.getParameter("version");
        String phoneType = request.getParameter("phoneType");
        String phoneNum = request.getParameter("phoneNum");
        String verCode = request.getParameter("verCode");


        String value = LocalCache.getInStance().getLocalCache(phoneNum + "_1") + "";
        LocalCache.getInStance().removeLocalCache(phoneNum + "_1");
        if ("1".equals(PropertiesFileUtil.getValue("DEBUG"))) value = "1234";
        if (verCode.equals(value)) {
            UserBean user = new UserBean();
            user.setPhone(phoneNum);

            List<UserBean> users = userService.queryList(user);
            if (users != null && users.size() > 0) {
                UserBean updateUser = new UserBean();
                updateUser.setId(users.get(0).getId());
                updateUser.setPhoneType(phoneType);
                updateUser.setVersion(version);
                updateUser.setUpdateTime(DateTimeUtil.getCurrentDateTimeStr());
                userService.update(updateUser);
                user = users.get(0);
            } else {
                user.setNickName("未设置");
                user.setAvatar("default.png");
                user.setSex(1);
                user.setRemark("这家伙很懒,什么都没写！");
                user.setToken(MD5.getMD5(DateTimeUtil.getCurrentDateTimeStr()));
                user.setVersion(version);
                user.setCreateTime(DateTimeUtil.getCurrentDateTimeStr());
                user.setPhoneType(phoneType);
                user.setLoginType(0);//手机号码登录
                userService.insert(user);
            }
            result.setCode(0);
            result.setData(user);
            return result;
        } else {
            result.setCode(1);
            result.setMsg("验证码错误");
            return result;
        }


    }

    ObjectMapper objectMapper = new ObjectMapper();
    @ResponseBody
    @RequestMapping(value = "/wechatLogin")
    public Result wechatLogin(HttpServletRequest request) throws Exception {
        Result result = new Result();

        String openid = request.getParameter("openid");
        String access_token = request.getParameter("access_token");
        String version = request.getParameter("version");
        String phoneType = request.getParameter("phoneType");
        UserBean user = new UserBean();
        user.setWechatId(openid);
        List<UserBean> users = userService.queryList(user);
        if (users != null && users.size() > 0) {
            user = users.get(0);
            user.setPhoneType(phoneType);
            user.setUpdateTime(DateTimeUtil.getCurrentDateTimeStr());
            userService.update(user);
        } else {
            String wechatRes = HttpUtil.get(PropertiesFileUtil.getValue("wechat_get_user_info_url")
                    + "?access_token=" + access_token + "&openid=" + openid);
            JsonNode jsonObject = objectMapper.readTree(wechatRes);
            String weChatNickName = jsonObject.get("nickname").asText();
            int sex = jsonObject.get("sex").asInt();
            String headimgurl = jsonObject.get("headimgurl").asText();
            user.setNickName(weChatNickName);
            user.setAvatar(headimgurl);
            user.setSex(sex == 1 ? 1 : 0);
            user.setRemark("这家伙很懒,什么都没写！");
            user.setToken(MD5.getMD5(DateTimeUtil.getCurrentDateTimeStr()));
            user.setVersion(version);
            user.setCreateTime(DateTimeUtil.getCurrentDateTimeStr());
            user.setPhoneType(phoneType);
            user.setLoginType(1);//微信登录
            userService.insert(user);
        }
        result.setCode(0);
        result.setData(user);
        return result;


    }


    @ResponseBody
    @RequestMapping(value = "/qqLogin")
    public Result qqLogin(HttpServletRequest request) throws Exception {
        Result result = new Result();

        String access_token = request.getParameter("access_token");
        String openid = request.getParameter("openid");
        String version = request.getParameter("version");
        String phoneType = request.getParameter("phoneType");
        UserBean user = new UserBean();
        user.setQqId(openid);
        List<UserBean> users = userService.queryList(user);
        if (users != null && users.size() > 0) {
            user = users.get(0);
            user.setPhoneType(phoneType);
            user.setVersion(version);
            user.setUpdateTime(DateTimeUtil.getCurrentDateTimeStr());
            userService.update(user);
        } else {
            String wechatRes = HttpUtil.get(PropertiesFileUtil.getValue("qq_get_user_info_url") + "?access_token="
                    + access_token + "&oauth_consumer_key=" + PropertiesFileUtil.getValue("qq_appid") + "&openid="
                    + openid);
            JsonNode jsonObject = objectMapper.readTree(wechatRes);
            String weChatNickName = jsonObject.get("nickname").asText();
            String sex = jsonObject.get("gender").asText();
            String headimgurl = jsonObject.get("figureurl_qq_1").asText();
            user.setNickName(weChatNickName);
            user.setAvatar(headimgurl);
            user.setSex("男".equals(sex) ? 1 : 0);
            user.setRemark("这家伙很懒,什么都没写！");
            user.setToken(MD5.getMD5(DateTimeUtil.getCurrentDateTimeStr()));
            user.setVersion(version);
            user.setCreateTime(DateTimeUtil.getCurrentDateTimeStr());
            user.setPhoneType(phoneType);
            user.setLoginType(2);//qq登录
            userService.insert(user);
        }
        result.setCode(0);
        result.setData(user);
        return result;


    }

    /***
     * 用户修改信息
     */
    @ResponseBody
    @RequestMapping(value = "/updateInfo")
    public Result updateInfo(HttpServletRequest request) throws Exception {

        String uid = request.getParameter("uid");
        String sex = request.getParameter("sex");
        String nickName = request.getParameter("nickName");

        if (StringUtils.isEmpty(uid)) {
            throw new UnLoginException("uid为空");
        }
        UserBean user = new UserBean();
        user.setId(Long.parseLong(uid));
        if (!StringUtils.isEmpty(sex)) {
            user.setSex(Integer.parseInt(sex));
        }
        if (!StringUtils.isEmpty(nickName)) {
            user.setNickName(nickName);
        }
        UserBean update = userService.update(user);
        Result result = new Result();
        result.setCode(0);
        result.setData(update);
        return result;


    }


    /***
     * 上传头像
     */
    @ResponseBody
    @RequestMapping(value = "/updateHeadImage")
    public Result updateHeadImage(MultipartFile file, String uid) throws IOException {
        if (StringUtils.isEmpty(uid)) {
            throw new UnLoginException("id为空");
        }

        UserBean userBean = userService.updateAvatar(file, uid);
        return Result.success(userBean);


    }


    @ResponseBody
    @RequestMapping(value = "/bindPhoneNum")
    public Result bindPhoneNum(HttpServletRequest request) throws Exception {
        Result result = new Result();
        String uid = request.getParameter("uid");
        String phoneNum = request.getParameter("phoneNum");
        String verCode = request.getParameter("verCode");

        if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(phoneNum) || StringUtils.isEmpty(verCode)) {
            throw new ParamErrorException("参数错误");
        }
        String value = LocalCache.getInStance().getLocalCache(phoneNum + "_2") + "";
        LocalCache.getInStance().removeLocalCache(phoneNum + "_2");
        if (verCode.equals(value)) {
            UserBean user = new UserBean();
            user.setId(Long.parseLong(uid));
            user.setPhone(phoneNum);
            userService.update(user);
            result.setCode(0);
        } else {
            result.setCode(1);
            result.setMsg("1001");
        }
        return result;
    }
}
