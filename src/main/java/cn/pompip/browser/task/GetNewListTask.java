package cn.pompip.browser.task;


import cn.pompip.browser.model.NewsBean;
import cn.pompip.browser.model.NewsContentBean;
import cn.pompip.browser.service.NewsService;
import cn.pompip.browser.util.HttpUtil;
import cn.pompip.browser.util.PropertiesFileUtil;
import cn.pompip.browser.util.date.DateTimeUtil;
import cn.pompip.browser.util.security.MD5;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Component
public class GetNewListTask {
    public static String[] newTypes = {"toutiao", "shehui", "guoji", "guonei", "yule", "keji", "junshi", "shishang", "caijing", "youxi", "qiche", "xiaohua", "jiankang", "tiyu", "xingzu", "kexue", "hulianwang", "shuma"};

    private Log log = LogFactory.getLog(GetNewListTask.class);

    @Autowired
    private NewsService newsService;


	
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

    @Scheduled(fixedDelay = 30 * 60 * 1000)
//    @Scheduled(cron = " 0/30 * * * * ?")
    public void run() {
        log.info("-------------------begin GetNewListTask-------------------");
        try {
            for (String type : newTypes) {
                log.info("get " + type + " count:" + this.getNewListByType(type));
            }
            log.info("-------------------end GetNewListTask-------------------");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("GetNewListTask error");
        }

    }


    public int getNewListByType(String type) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("qid", PropertiesFileUtil.getValue("news_qid"));
        String responsesStr = HttpUtil
                .get(PropertiesFileUtil.getValue("news_list_url"), params);

        JSONObject jsonObject = new JSONObject(responsesStr);


        String stat = jsonObject.getString("stat");
        if ("1".equals(stat)) {
            JSONArray list = jsonObject.getJSONArray("data");
//            List<NewsBean> addNews = new ArrayList<>();

            for (int i = 0; i < list.length(); i++) {
                JSONObject news = list.getJSONObject(i);
                saveNews(news);

            }

        }
        return 0;
    }

    @Async
    void saveNews(JSONObject newsObject) {
        String url = newsObject.getString("url");
        String urlmd5 = MD5.getMD5(url);

        NewsBean news = new NewsBean();
        news.setContent(newsObject.toString());
        news.setTitle(newsObject.getString("topic"));
        news.setSource(newsObject.getString("source"));
        news.setPublishTime(newsObject.getString("date"));
        news.setMiniimgSize(newsObject.getString("miniimg_size"));
        news.setMiniimg(newsObject.getJSONArray("miniimg").toString());
        news.setRowkey(newsObject.getString("rowkey"));
        news.setSource(newsObject.getString("source"));
        news.setNewsType(newsObject.getString("type"));
        news.setType(1);
        news.setUrl(url);
        news.setUrlmd5(urlmd5);
        news.setCreateTime(DateTimeUtil.getCurrentDateTimeStr());

        try {
            NewsContentBean newsContentBean = generateContent(url);
            newsService.insertNews(news, newsContentBean);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


    }
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private NewsContentBean generateContent(String url) throws IOException, ParseException {
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
        newsContentBean.setNewsTime(DateTimeUtil.formatDate(simpleDateFormat.parse(time)));
        newsContentBean.setCreateTime(DateTimeUtil.getCurrentDateTimeStr());
        newsContentBean.setNewsTitle(title);
        newsContentBean.setUrl(url);
        return newsContentBean;

    }

}
