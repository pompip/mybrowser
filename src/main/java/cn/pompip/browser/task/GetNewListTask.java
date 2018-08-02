package cn.pompip.browser.task;


import cn.pompip.browser.model.NewsBean;
import cn.pompip.browser.model.NewsContentBean;
import cn.pompip.browser.service.NewsService;
import cn.pompip.browser.util.HttpUtil;
import cn.pompip.browser.util.date.DateTimeUtil;
import cn.pompip.browser.util.security.MD5;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GetNewListTask {
    /*toutiao	头条
	shehui	社会
	guoji	国际
	guonei	国内
	yule	娱乐
	keji	科技
	junshi	军事
	shishang	时尚
	caijing	财经
	youxi	游戏
	qiche	汽车
	xiaohua	笑话
	jiankang	健康
	tiyu	体育
	xingzuo	星座
	kexue	科学
	hulianwang	互联网
	shuma	数码*/
    public static String[] newTypes = {"toutiao","shehui", "guoji",
            "guonei", "yule", "keji", "junshi", "shishang", "caijing",
            "youxi", "qiche", "xiaohua", "jiankang", "tiyu", "xingzu",
            "kexue", "hulianwang", "shuma","lvyou","qinggan","xinwen","yuer","lishi"};


    private Log log = LogFactory.getLog(GetNewListTask.class);

    @Autowired
    private NewsService newsService;

    @Scheduled(fixedDelay = 30 * 60 * 1000)
@Async
    public void run() {
        log.info("-------------------begin GetNewListTask-------------------");
        for (String type : newTypes) {
            int generateResult = generateResult(type);
            log.info("get " + type +" num: "+ generateResult);

        }
        log.info("-------------------end GetNewListTask-------------------");

    }

    public int generateResult(String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("qid", "qid02561");
        int num = 0;
        try {
            ResponseBody responseBody = HttpUtil.get("http://newswifiapi.dftoutiao.com/jsonnew/refresh", params);
            ArrayList<NewsBean> newsBeanArrayList = parseResult(responseBody.byteStream(),type);
            for (NewsBean newsBean : newsBeanArrayList) {
                try {
                    NewsContentBean newsContentBean = generateContent(newsBean.getUrl());
                    if (newsContentBean!=null){
                        newsService.insertNews(newsBean, newsContentBean);
                        num++;
                    }
                }catch (Exception e){
                    log.error("插入新闻报错",e);
                }
            }

        } catch (IOException e) {
            log.error("获取新闻列表报错",e);
        }
        return num;
    }

    @Autowired
    ObjectMapper objectMapper;

    ArrayList<NewsBean> parseResult(InputStream result,String type) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(result);
        String stat = jsonNode.findValue("stat").asText();
        ArrayList<NewsBean> newsBeanArrayList = new ArrayList<>();
        if ("1".equals(stat)) {
            List<JsonNode> data = jsonNode.findValues("data");
            log.info("获取 " + data.size() + "条新闻");
            data.forEach(jsonNode1 -> {
                        NewsBean newsBean = saveResult(jsonNode1);
                        if (newsBean != null) {
                        newsBean.setNewsType(type);
                            newsBeanArrayList.add(newsBean);
                        }
                    }
            );
        }
        return newsBeanArrayList;
    }

    NewsBean saveResult(JsonNode jsonNode) {
        JsonNode urlNode = jsonNode.findValue("url");
        if (urlNode == null) {
            return null;
        }
        String url = urlNode.asText();
        String urlmd5 = MD5.getMD5(url);

        NewsBean news = new NewsBean();
        news.setTitle(jsonNode.findValue("topic").asText());
        news.setSource(jsonNode.findValue("source").asText());
        news.setPublishTime(jsonNode.findValue("date").asText());
        news.setMiniimgSize(jsonNode.findValue("miniimg_size").toString());
        news.setMiniimg(jsonNode.findValue("miniimg").toString());
        news.setRowkey(jsonNode.findValue("rowkey").asText());
        news.setSource(jsonNode.findValue("source").asText());
        news.setNewsType(jsonNode.findValue("type").asText());
        news.setUrl(url);
        news.setUrlmd5(urlmd5);
        news.setCreateTime(DateTimeUtil.getCurrentDateTimeStr());


        return news;
    }


    private NewsContentBean generateContent(String url) throws IOException {
        Document htmlDom = Jsoup.connect(url).get();
        String title = htmlDom.getElementsByClass("title").get(0).text();
        String timeSrc = htmlDom.getElementsByClass("src").get(0).text();
        String content = htmlDom.getElementById("content").html().toString();
        String[] timeSrcArr = timeSrc.split("来源：");
        String time = timeSrcArr[0].replaceAll("&nbsp;", "");
        String source = timeSrcArr.length > 1 ? timeSrcArr[1] : "";

        NewsContentBean newsContentBean = new NewsContentBean();
        newsContentBean.setAuthor(source);
        newsContentBean.setNewsContent(content);
        newsContentBean.setNewsTime(DateTimeUtil.formatDate(DateTimeUtil.string2Date(time)));
        newsContentBean.setCreateTime(DateTimeUtil.getCurrentDateTimeStr());
        newsContentBean.setNewsTitle(title);
        newsContentBean.setUrl(url);
        return newsContentBean;

    }

}
