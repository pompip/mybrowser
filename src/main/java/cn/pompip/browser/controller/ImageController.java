package cn.pompip.browser.controller;

import cn.pompip.browser.model.ImageBean;
import cn.pompip.browser.service.ImageServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/img")
@Controller
public class ImageController {


    @Autowired
    ImageServer imageServer;

    @ResponseBody
    @RequestMapping("/upload")
    public String uploadImage(MultipartFile img) throws IOException {
        ImageBean imageBean = imageServer.saveImage(img);
        return imageServer.generateImageUrl(imageBean);

    }

    @RequestMapping("/get/{imageID}")
    @ResponseBody
    public void getImage(@PathVariable long imageID, HttpServletResponse response) throws IOException {
        byte[] bytes = Base64Utils.decodeFromString(imageServer.findImageByID(imageID));
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
}
