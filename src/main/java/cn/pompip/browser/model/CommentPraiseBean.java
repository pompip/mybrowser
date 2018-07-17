package cn.pompip.browser.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
@Entity
public class CommentPraiseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8077386532452953302L;

	@Id
	@GeneratedValue
	private Long id;//主键
	
	private String code;
	
	private Integer status;
	
	private Long fromUid;
	
	private Long commentId;

	private String createTime;
	
	private String updateTime;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getFromUid() {
		return fromUid;
	}

	public void setFromUid(Long fromUid) {
		this.fromUid = fromUid;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
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
	

	
}
