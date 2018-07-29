package cn.pompip.browser.model;

import cn.pompip.browser.util.json.serializer.BrowserJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

@Entity
public class VideoBean {
    @Id
    @GeneratedValue
    private Long id;
    private String url;
    private String title;
    private String source;
    @Column(columnDefinition = "JSON")
    private String miniimg;
    private String itemId;
    private String groupId;
    private String videoId;
    @Column(columnDefinition = "DATETIME")
    private String publishTime;
    @Column(columnDefinition = "DATETIME")
    private String createTime;
    private String videoDuration;
    @Column(unique = true)
    private String urlmd5;
    private String newsType;
    @Transient
    private String type = "2";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }



    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }



    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getUrlmd5() {
        return urlmd5;
    }

    public void setUrlmd5(String urlmd5) {
        this.urlmd5 = urlmd5;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }
    @JsonSerialize(using = BrowserJsonSerializer.class)
    public String getMiniimg() {
        return miniimg;
    }

    public void setMiniimg(String miniimg) {
        this.miniimg = miniimg;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }
}
