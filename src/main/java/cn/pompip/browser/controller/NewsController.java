package cn.pompip.browser.controller;


import cn.pompip.browser.common.entity.Result;
import cn.pompip.browser.model.NewsBean;
import cn.pompip.browser.model.NewsContentBean;
import cn.pompip.browser.service.NewsService;
import cn.pompip.browser.util.HttpUtil;
import cn.pompip.browser.util.security.Base64;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    @Autowired
    ObjectMapper objectMapper;


    @ResponseBody
    @RequestMapping(value = "/getNewsList")
    public Result getNewList(HttpServletRequest request )  {
//        String type = request.getParameter("type");
        String newsType=request.getParameter("newsType");
        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");

        NewsBean newsBean = new NewsBean();
//        newsBean.setType(Integer.parseInt(type));
        newsBean.setNewsType(newsType);
        List<NewsBean> newsBeanList = newsService.pageList(newsBean, Integer.parseInt(pageNum), Integer.parseInt(pageSize));


        return Result.success(newsBeanList);

    }

    @ResponseBody
    @RequestMapping(value = "/getNewInfo")
    public Result getNewsInfo(HttpServletRequest request) throws Throwable {
        String id = request.getParameter("id");
        String uid = request.getParameter("uid");

        NewsContentBean newsContentBean = newsService.findNewsContentByID(Long.parseLong(id));

        return Result.success(newsContentBean);

    }

    @RequestMapping(value = "/share")
    public ModelAndView shareNew(HttpServletRequest request , ModelAndView mav) throws IOException {
        String id = request.getParameter("id");
        //String urlMd5=request.getParameter("urlMd5");
        //String url=request.getParameter("url");
        //String type=request.getParameter("type");

//        NewsBean newsBean = newsService.getById(Long.parseLong(id));
//
//        Document htmlDom = parseHtmlFromString(newsBean.getUrl());
//        String title = htmlDom.getElementsByClass("title").get(0).text();
//        String timeSrc = htmlDom.getElementsByClass("src").get(0).text();
//        String content = htmlDom.getElementById("content").html().toString();
//        String[] timeSrcArr = timeSrc.split("来源：");
//        String time = timeSrcArr[0].replaceAll("&nbsp;", "");
//        String source = timeSrcArr.length > 1 ? timeSrcArr[1] : "";
//        mav.addObject("title", title);
//        mav.addObject("time", time);
//        mav.addObject("source", source);
//        mav.addObject("content", content);
//        mav.setViewName("new/detail");
        return mav;

    }

    @ResponseBody
    @RequestMapping(value = "/getVideoInfo")
    public Result getVideoInfo(HttpServletRequest request)  {
        Result result = new Result();
        String id = request.getParameter("id");
        String uid = request.getParameter("uid");
        String url = request.getParameter("url");

        NewsBean newsBean = newsService.getById(Long.parseLong(id));
        String videoUrl = "http://ib.365yg.com";
        String r = System.currentTimeMillis() + "";
        String params = "/video/urls/v/1/toutiao/mp4/" + newsBean.getVideoId() + "?r=" + r;
        CRC32 crc32 = new CRC32();
        crc32.update(params.getBytes());
        videoUrl = videoUrl + params + "&s=" + crc32.getValue();
        JsonNode jsonObject =null;
        try {
            String data  = HttpUtil.get(videoUrl);
             jsonObject = objectMapper.readTree(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        if ("success".equals(jsonObject.get("message").asText()) && jsonObject.get("total").asInt() > 0) {
            JsonNode videoData = jsonObject.get("data").get("video_list").get("video_1");
            Map<String,String> resultMap = new HashMap<>();
            resultMap.put("title", newsBean.getTitle());
            resultMap.put("main_url", Base64.decodeString(videoData.get("main_url").asText()));
            resultMap.put("backup_url_1", Base64.decodeString(videoData.get("backup_url_1").asText()));
            result.setCode(0);
            result.setData(resultMap);
        } else {
            result.setCode(1);
            result.setCode(1001);
        }
        return result;
    }

    @GetMapping("/newsContentH5")
    public Map<String,String> getNewsContentH5(String id ){
        Map<String ,String> map = new HashMap<>();

        return map;
    }



}
