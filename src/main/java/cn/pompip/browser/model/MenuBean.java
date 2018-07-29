package cn.pompip.browser.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class MenuBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8077386532452953302L;
    @Id
    @GeneratedValue
    private Long id;//主键

    private String title;

    private String icon;

    private String type;

    private String newsType;

    private Integer sort;

    private String createTime;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }
}
