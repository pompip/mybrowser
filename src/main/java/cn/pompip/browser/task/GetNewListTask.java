package cn.pompip.browser.task;


import cn.pompip.browser.model.NewBean;
import cn.pompip.browser.service.NewsService;
import cn.pompip.browser.util.HttpClientUtil;
import cn.pompip.browser.util.PropertiesFileUtil;
import cn.pompip.browser.util.date.DateTimeUtil;
import cn.pompip.browser.util.security.MD5;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Scheduled(cron = "0 0/30 * * * ?")
//    @Scheduled(cron = " 0/30 * * * * ?")
    @Transactional
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

    @SuppressWarnings("unused")
    public int getNewListByType(String type) throws Exception {

        String responsesStr = HttpClientUtil
                .get(PropertiesFileUtil.getValue("news_list_url") + "?type=" + type + "&qid=" + PropertiesFileUtil.getValue("news_qid"));

        JSONObject jsonObject = new JSONObject(responsesStr);


        String stat = jsonObject.getString("stat");
        if ("1".equals(stat)) {
            JSONArray list = jsonObject.getJSONArray("data");
            List<NewBean> addNews = new ArrayList<>();

            for (int i = 0; i < list.length(); i++) {
                JSONObject news = list.getJSONObject(i);
                String url = news.getString("url");
                String urlmd5 = MD5.getMD5(url);

                NewBean addNew = new NewBean();
                addNew.setContent(news.toString());
                addNew.setTopic(news.getString("topic"));
                addNew.setSource(news.getString("source"));
                addNew.setDate(news.getString("date"));
                addNew.setMiniimgSize(news.getString("miniimg_size"));
                addNew.setMiniimg(news.getJSONArray("miniimg").toString());
                addNew.setRowkey(news.getString("rowkey"));
                addNew.setSource(news.getString("source"));
                addNew.setNewType(news.getString("type"));
                addNew.setType(1);
                addNew.setUrl(url);
                addNew.setUrlmd5(urlmd5);
                addNew.setCreateTime(DateTimeUtil.getCurrentDateTimeStr());
                addNews.add(addNew);

            }
            return newsService.insertNewsBatch(addNews);
        }
        return 0;
    }

}
