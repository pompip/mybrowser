package cn.pompip.browser.controller;


import cn.pompip.browser.common.entity.Result;
import cn.pompip.browser.exception.NoResultException;
import cn.pompip.browser.model.NewsBean;
import cn.pompip.browser.model.NewsContentBean;
import cn.pompip.browser.model.VideoContentBean;
import cn.pompip.browser.service.NewsService;
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
    public Result getVideoInfo(HttpServletRequest request) throws NoResultException {

        String id = request.getParameter("id");
        String uid = request.getParameter("uid");
        String url = request.getParameter("url");


        VideoContentBean videoContentBean = newsService.findVideoBeanById(Long.parseLong(id));
        return Result.success(videoContentBean);
    }

    @GetMapping("/newsContentH5")
    public Map<String,String> getNewsContentH5(String id ){
        Map<String ,String> map = new HashMap<>();

        return map;
    }



}
