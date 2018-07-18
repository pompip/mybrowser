package cn.pompip.browser.model;

import cn.pompip.browser.util.json.serializer.BrowserJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class NewBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8077386532452953302L;
    @Id
    @GeneratedValue
    private Long id;//主键

    private String code;

    @Column(unique = true)
    private String urlmd5;

    private String url;
    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer status;

    private Integer type;

    private String newsType;

    private String source;

    private String miniimgSize;


    @Column(columnDefinition = "JSON")
    private String miniimg;

    private String rowkey;

    private String title;

    @JsonSerialize(using = BrowserJsonSerializer.class)
    private String largeImageList;

    private String itemId;

    private String groupId;

    private String videoId;

    private String videoDuration;

    private String createTime;

    private String updateTime;


    private String remark;

    @Column(columnDefinition = "DATETIME")
    private String publishTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrlmd5() {
        return urlmd5;
    }

    public void setUrlmd5(String urlmd5) {
        this.urlmd5 = urlmd5;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMiniimgSize() {
        return miniimgSize;
    }

    public void setMiniimgSize(String miniimgSize) {
        this.miniimgSize = miniimgSize;
    }
    @JsonSerialize(using = BrowserJsonSerializer.class)
    public String getMiniimg() {
        return miniimg;
    }

    public void setMiniimg(String miniimg) {
        this.miniimg = miniimg;
    }

    public String getRowkey() {
        return rowkey;
    }

    public void setRowkey(String rowkey) {
        this.rowkey = rowkey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLargeImageList() {
        return largeImageList;
    }

    public void setLargeImageList(String largeImageList) {
        this.largeImageList = largeImageList;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public String getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }


}
