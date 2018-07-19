package cn.pompip.browser.service;

import cn.pompip.browser.dao.ImageDao;
import cn.pompip.browser.model.ImageBean;
import cn.pompip.browser.util.PropertiesFileUtil;
import org.apache.http.conn.util.InetAddressUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ImageServer {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    ImageDao imageDao;

    public ImageBean saveImage(MultipartFile img) throws IOException {
        String createTime = simpleDateFormat.format(new Date());
        ImageBean imageBean = new ImageBean();
        imageBean.setImageData(Base64Utils.encodeToString(img.getBytes()));
        imageBean.setImageName(createTime + "_" + img.getOriginalFilename());
        imageBean.setContentType(img.getContentType());
        imageBean.setImageSize(img.getSize());
        imageBean.setCreateTime(createTime);
        return imageDao.save(imageBean);
    }

    public String generateImageUrl(ImageBean imageBean) {
        return PropertiesFileUtil.getValue("server_address") + "img/get/" + imageBean.getId();
    }

    public String findImageByID(long imageID) {
        return imageDao.getOne(imageID).getImageData();
    }
}
