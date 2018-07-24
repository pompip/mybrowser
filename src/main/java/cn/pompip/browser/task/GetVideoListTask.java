package cn.pompip.browser.task;


import cn.pompip.browser.model.NewsBean;
import cn.pompip.browser.service.NewsService;
import cn.pompip.browser.util.HttpUtil;
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
import org.springframework.util.StringUtils;

import java.util.*;

@Component
public class GetVideoListTask {
    public static String[] videoTypes = {"video", "subv_comedy", "subv_funny", "subv_movie", "subv_entertainment", "subv_boutique", "subv_broaden_view"};

    private Log log = LogFactory.getLog(GetVideoListTask.class);

    @Autowired
    private NewsService newsService;


    /*video	推荐
    subv_life	生活
    subv_comedy	小品
    subv_society	社会
    subv_funny	搞笑
    subv_voice	音乐
    subv_movie	电影
    subv_entertainment	娱乐
    subv_cute	呆萌
    subv_game	游戏
    subv_boutique	原创
    subv_broaden_view	开眼*/
//    @Scheduled(cron = " 0/30 * * * * ?")
    @Scheduled(fixedDelay = 30*60*1000)
    @Transactional
    public void run() {
        log.info("-------------------begin GetVideoListTask-------------------");
        try {
            for (String type : videoTypes) {
                log.info("get " + type + " count:" + this.getVideoListByType(type));
            }
            log.info("-------------------end GetVideoListTask-------------------");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("GetVideoListTask error");
        }

    }

    @SuppressWarnings("unused")
    private int getVideoListByType(String type) throws Exception {
        Map<String, Object> postData = new HashMap<String, Object>();
        postData.put("category", type);
        postData.put("refer", "1");
        postData.put("count", "20");
        postData.put("min_behot_time", System.currentTimeMillis());
        postData.put("last_refresh_sub_entrance_interval", System.currentTimeMillis());
        postData.put("loc_mode", "7");
        postData.put("loc_time", System.currentTimeMillis());
        postData.put("latitude", 11);
        postData.put("longitude", 100);
        postData.put("city", "北京");
        postData.put("tt_from", "pull");
        postData.put("cp", "50ac6a6a0a581q1");
        postData.put("strict", "1");
        postData.put("iid", "24142389236");
        postData.put("device_id", "869271023736728");
        postData.put("ac", "unknown");
        postData.put("channel", "update");
        postData.put("aid", "1183");
        postData.put("app_name", "toutiaoribao_news");
        postData.put("version_code", "600");
        postData.put("version_name", "6.0.0");
        postData.put("device_platform", "android");
        postData.put("ab_client", "a1%2Cc4%2Ce1%2Cf2%2Cg2%2Cf7");
        postData.put("ab_group", "z1");
        postData.put("ab_feature", "z1");
        postData.put("abflag", "3");
        postData.put("ssmix", "a");
        postData.put("device_type", "Redmi Note 3");
        postData.put("device_brand", "Xiaomi");
        postData.put("language", "zh");
        postData.put("os_api", "25");
        postData.put("os_version", "7.1.1");
        postData.put("uuid", "869271023736728");
        postData.put("openudid", "5eceab483511e317");
        postData.put("manifest_version_code", "600");
        postData.put("resolution", "1080*1920");
        postData.put("dpi", "480");
        postData.put("update_version_code", "6002");
        postData.put("_rticket", System.nanoTime());
        String getUrl = PropertiesFileUtil.getValue("news_video_url") + "?";
//        for (String key : postData.keySet()) {
//            getUrl += key + "=" + URLEncoder.encode(postData.get(key).toString()) + "&";
//        }

        String data = HttpUtil.get( getUrl,postData);
        JSONObject jsonObject = new JSONObject(data);
        String code = jsonObject.getString("message");
        int total = jsonObject.getInt("total_number");
        if ("success".equals(code) && total > 0) {
            List<NewsBean> addNews = new ArrayList<NewsBean>();
            JSONArray list = jsonObject.getJSONArray("data");


            for (int i = 0; i < list.length(); i++) {
                JSONObject news = list.getJSONObject(i);
                String contentStr = news.getString("content");
                JSONObject content = new JSONObject(contentStr);

                if (!content.has("url") || StringUtils.isEmpty(content.getJSONArray("large_image_list").toString())) {
                    continue;
                }
                String urlMd5 = MD5.getMD5(content.getString("url"));
                NewsBean newsBean = new NewsBean();
                newsBean.setContent(content.toString());
                newsBean.setUrl(content.getString("url"));
                newsBean.setTitle(content.has("title") ? content.getString("title") : "");
                newsBean.setSource(content.getString("source").replaceAll("[\\x{10000}-\\x{10FFFF}]", ""));
                newsBean.setMiniimg(content.getJSONArray("large_image_list").toString());
                newsBean.setItemId(content.get("item_id").toString());
                newsBean.setGroupId(content.get("group_id").toString());
                newsBean.setVideoId(content.get("video_id").toString());
                newsBean.setPublishTime(DateTimeUtil.formatDate(content.getLong("publish_time")*1000));
                try {
                    newsBean.setVideoDuration(content.get("video_duration").toString());
                }catch (Exception e){
                    newsBean.setVideoDuration("100");
                }

                newsBean.setUrlmd5(urlMd5);
                newsBean.setType(2);
                newsBean.setNewsType(type);

                newsBean.setCreateTime(DateTimeUtil.getCurrentDateTimeStr());
                addNews.add(newsBean);


            }
             newsService.insertVideoBatch(addNews);
        }
        return 0;
    }

}
