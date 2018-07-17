package cn.pompip.browser.controller;

import cn.pompip.browser.common.entity.Result;
import cn.pompip.browser.model.UserBean;
import cn.pompip.browser.service.UserService;
import cn.pompip.browser.util.Cache.LocalCache;
import cn.pompip.browser.util.HttpClientUtil;
import cn.pompip.browser.util.PropertiesFileUtil;
import cn.pompip.browser.util.date.DateTimeUtil;
import cn.pompip.browser.util.security.MD5;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("/user")
@Controller
public class UserController {


    @Autowired
    private UserService userService;

    /***
     * 手机号登录
     */
    @ResponseBody
    @RequestMapping(value = "/phoneNumLogin.html")
    public Result phoneNumLogin(HttpServletRequest request) {
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
                user.setIcon("default.png");
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

    @ResponseBody
    @RequestMapping(value = "/wechatLogin.html")
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
            String wechatRes = HttpClientUtil.get(PropertiesFileUtil.getValue("wechat_get_user_info_url") + "?access_token=" + access_token + "&openid=" + openid);
            JSONObject jsonObject = new JSONObject(wechatRes);
            String weChatNickName = jsonObject.getString("nickname");
            int sex = jsonObject.getInt("sex");
            String headimgurl = jsonObject.getString("headimgurl");
            user.setNickName(weChatNickName);
            user.setIcon(headimgurl);
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
    @RequestMapping(value = "/qqLogin.html")
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
            String wechatRes = HttpClientUtil.get(PropertiesFileUtil.getValue("qq_get_user_info_url") + "?access_token=" + access_token + "&oauth_consumer_key=" + PropertiesFileUtil.getValue("qq_appid") + "&openid=" + openid);
            JSONObject jsonObject = new JSONObject(wechatRes);
            String weChatNickName = jsonObject.getString("nickname");
            String sex = jsonObject.getString("gender");
            String headimgurl = jsonObject.getString("figureurl_qq_1");
            user.setNickName(weChatNickName);
            user.setIcon(headimgurl);
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
    @RequestMapping(value = "/updateInfo.html")
    public Result updateInfo(HttpServletRequest request) {
        Result result = new Result();
        String uid = request.getParameter("uid");
        String sex = request.getParameter("sex");
        String nickName = request.getParameter("nickName");

            if (StringUtils.isEmpty(uid)) {
                result.setCode(101);
                result.setMsg("参数错误");
                return result;
            }
            UserBean user = new UserBean();
            user.setId(Long.parseLong(uid));
            if (!StringUtils.isEmpty(sex)) {
                user.setSex(Integer.parseInt(sex));
            }
            if (!StringUtils.isEmpty(nickName)) {
                user.setNickName(nickName);
            }
            userService.update(user);
            result.setCode(0);
            return result;




    }


    /***
     * 上传头像
     */
    @ResponseBody
    @RequestMapping(value = "/updateHeadImage.html")
    public Result updateHeadImage(HttpServletRequest request) throws IOException {
        Result result = new Result();
        String uid = request.getParameter("uid");

        if (StringUtils.isEmpty(uid)) {
            result.setCode(101);
            result.setMsg("参数错误");
            return result;
        }
        this.uploadHeadImage(request, uid);
        UserBean user = new UserBean();
        user.setId(Long.parseLong(uid));
        user.setIcon(PropertiesFileUtil.getValue("aliyun_oss_url") + "/headImages/" + uid + ".png");
        userService.update(user);
        Map<String, String> data = new HashMap<String, String>();
        data.put("avatar", user.getIcon());
        result.setCode(0);
        result.setData(data);
        return result;


    }

    /***
     * 头像上传到阿里云oss
     * @param request
     * @param uid
     * @throws IOException
     */
    private void uploadHeadImage(HttpServletRequest request, String uid) throws IOException {
        InputStream is = request.getInputStream();
        OSSClient ossClient = new OSSClient(PropertiesFileUtil.getValue("aliyun_oss_endpoint"), PropertiesFileUtil.getValue("aliyun_accessKeyId"), PropertiesFileUtil.getValue("aliyun_accessKeySecret"));
        ossClient.putObject(new PutObjectRequest(PropertiesFileUtil.getValue("aliyun_oss_bucketName"), "headImages/" + uid + ".png", is));
        ossClient.shutdown();
    }

    @ResponseBody
    @RequestMapping(value = "/bindPhoneNum.html")
    public Result bindPhoneNum(HttpServletRequest request) {
        Result result = new Result();
        String uid = request.getParameter("uid");
        String phoneNum = request.getParameter("phoneNum");
        String verCode = request.getParameter("verCode");

        if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(phoneNum) || StringUtils.isEmpty(verCode)) {
            result.setCode(101);
            result.setMsg("参数错误");
            return result;
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
