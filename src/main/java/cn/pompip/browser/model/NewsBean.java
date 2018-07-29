package cn.pompip.browser.model;

import cn.pompip.browser.util.json.serializer.BrowserJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;

@Entity()
@Table(name = "new_bean")
public class NewsBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8077386532452953302L;
    @Id
    @GeneratedValue
    private Long id;//主键

    @Column(unique = true)
    private String urlmd5;

    private String url;
    private String newsType;

    private String source;

    private String miniimgSize;


    @Column(columnDefinition = "JSON")
    private String miniimg;

    private String rowkey;

    private String title;


    private String createTime;

    private String updateTime;


    @Column(columnDefinition = "DATETIME")
    private String publishTime;
    @Transient
    private String type = "1";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getType() {
        return type;
    }
}
