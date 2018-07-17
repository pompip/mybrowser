package cn.pompip.browser.controller;


import cn.pompip.browser.common.entity.Result;
import cn.pompip.browser.model.NewBean;
import cn.pompip.browser.service.NewsService;
import cn.pompip.browser.util.HttpClientUtil;
import cn.pompip.browser.util.security.Base64;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;


@RequestMapping("/news")
@Controller
public class NewsController {


    @Autowired
    private NewsService newsService;


    @ResponseBody
    @RequestMapping(value = "/getNewsList")
    public Result getNewList(HttpServletRequest request )  {
        String type = request.getParameter("type");
//        String newType=request.getParameter("newType");
        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");

        NewBean newBean = new NewBean();
        newBean.setType(Integer.parseInt(type));
//        newBean.setNewType(newType);
        List<NewBean> newBeanList = newsService.pageList(newBean, Integer.parseInt(pageNum), Integer.parseInt(pageSize));


        return Result.success(newBeanList);

    }

    @ResponseBody
    @RequestMapping(value = "/getNewInfo.html")
    public Result getNewInfo(HttpServletRequest request) throws IOException {
        String id = request.getParameter("id");
        String uid = request.getParameter("uid");
        String urlMd5 = request.getParameter("urlMd5");

        NewBean newBean = newsService.getById(Long.parseLong(id));
        Document htmlDom = this.parseHtmlFromString(newBean.getUrl());
        String title = htmlDom.getElementsByClass("title").get(0).text();
        String timeSrc = htmlDom.getElementsByClass("src").get(0).text();
        String content = htmlDom.getElementById("content").html().toString();
        String[] timeSrcArr = timeSrc.split("来源：");
        String time = timeSrcArr[0].replaceAll("&nbsp;", "");
        String source = timeSrcArr.length > 1 ? timeSrcArr[1] : "";
        Map resultMap = new HashMap();
        resultMap.put("title", title);
        resultMap.put("time", time);
        resultMap.put("source", source);
        resultMap.put("content", content);
        resultMap.put("urlmd5", urlMd5);
        resultMap.put("id", id);

        return Result.success(resultMap);

    }

    @RequestMapping(value = "/share.html")
    public ModelAndView shareNew(HttpServletRequest request , ModelAndView mav) throws IOException {
        String id = request.getParameter("id");
        //String urlMd5=request.getParameter("urlMd5");
        //String url=request.getParameter("url");
        //String type=request.getParameter("type");

        NewBean newBean = newsService.getById(Long.parseLong(id));
        Document htmlDom = this.parseHtmlFromString(newBean.getUrl());
        String title = htmlDom.getElementsByClass("title").get(0).text();
        String timeSrc = htmlDom.getElementsByClass("src").get(0).text();
        String content = htmlDom.getElementById("content").html().toString();
        String[] timeSrcArr = timeSrc.split("来源：");
        String time = timeSrcArr[0].replaceAll("&nbsp;", "");
        String source = timeSrcArr.length > 1 ? timeSrcArr[1] : "";
        mav.addObject("title", title);
        mav.addObject("time", time);
        mav.addObject("source", source);
        mav.addObject("content", content);
        mav.setViewName("new/detail");
        return mav;

    }

    @ResponseBody
    @RequestMapping(value = "/getVideoInfo.html")
    public Result getVideoInfo(HttpServletRequest request) throws Exception {
        Result result = new Result();
        String id = request.getParameter("id");
        String uid = request.getParameter("uid");
        String url = request.getParameter("url");

        NewBean newBean = newsService.getById(Long.parseLong(id));
        String videoUrl = "http://ib.365yg.com";
        String r = System.currentTimeMillis() + "";
        String params = "/video/urls/v/1/toutiao/mp4/" + newBean.getVideoId() + "?r=" + r;
        CRC32 crc32 = new CRC32();
        crc32.update(params.getBytes());
        videoUrl = videoUrl + params + "&s=" + crc32.getValue();
        String data = HttpClientUtil.get(videoUrl);
        JSONObject jsonObject = new JSONObject(data);
        if ("success".equals(jsonObject.getString("message")) && jsonObject.getInt("total") > 0) {
            JSONObject videoData = jsonObject.getJSONObject("data").getJSONObject("video_list").getJSONObject("video_1");
            Map resultMap = new HashMap();
            resultMap.put("title", newBean.getTitle());
            resultMap.put("main_url", Base64.decodeString(videoData.getString("main_url")));
            resultMap.put("backup_url_1", Base64.decodeString(videoData.getString("backup_url_1")));
            result.setCode(0);
            result.setData(resultMap);
        } else {
            result.setCode(1);
            result.setCode(1001);
        }
        return result;

    }


    private static Document parseHtmlFromString(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

}
