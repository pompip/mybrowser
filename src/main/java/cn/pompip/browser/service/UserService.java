package cn.pompip.browser.service;


import cn.pompip.browser.dao.UserDao;
import cn.pompip.browser.exception.ParamErrorException;
import cn.pompip.browser.model.ImageBean;
import cn.pompip.browser.model.UserBean;
import cn.pompip.browser.util.MyBeanUtils;
import cn.pompip.browser.util.PropertiesFileUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao dao;

    @Autowired
    private ImageServer imageServer;

    public int insert(UserBean t) {
        dao.save(t);
        return 0;
    }

    public UserBean updateAvatar(MultipartFile file, String uid) throws IOException {
        ImageBean imageBean = imageServer.saveImage(file);
        String imageUrl = imageServer.generateImageUrl(imageBean);
        UserBean user = new UserBean();
        user.setId(Long.parseLong(uid));
        user.setAvatar(imageUrl);
        return dao.save(user);

    }

    public UserBean update(MultipartFile file, String uid) {
        InputStream is = null;
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            throw new ParamErrorException("图片读取错误");
        }
        OSSClient ossClient = new OSSClient(PropertiesFileUtil.getValue("aliyun_oss_endpoint"),
                PropertiesFileUtil.getValue("aliyun_accessKeyId"), PropertiesFileUtil.getValue("aliyun_accessKeySecret"));

        String avatarPath = "headImages/" + uid + "_" + System.currentTimeMillis() + ".png";
        ossClient.putObject(new PutObjectRequest(PropertiesFileUtil.getValue("aliyun_oss_bucketName"), avatarPath, is));
        ossClient.shutdown();

        UserBean user = new UserBean();
        user.setId(Long.parseLong(uid));
        user.setAvatar(PropertiesFileUtil.getValue("aliyun_oss_url") + "/" + avatarPath);
        return dao.save(user);
    }

    public UserBean update(UserBean user) throws Exception {

        UserBean userBean = dao.getOne(user.getId());

        MyBeanUtils.mergeNotNullProperty(user,userBean);
        return dao.save(userBean);
    }


    public UserBean getById(long id) {
        return dao.getOne(id);
    }


    public List<UserBean> queryList(UserBean t) {
        return dao.findAll(Example.of(t));
    }


}
