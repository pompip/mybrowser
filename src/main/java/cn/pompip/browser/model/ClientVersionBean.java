package cn.pompip.browser.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
@Entity
public class ClientVersionBean implements Serializable {

	private static final long serialVersionUID = -8908394569358961L;
	@Id
	@GeneratedValue
	private long id;

	private String code; //版本号

 	private int status; //版本状态 1:失效 0:正常
 
	private String downloadUrl; //下载地址

	private int type; //类型 1:安卓

	private String size; //大小

	private String createTime; //创建时间

	private String updateTime; //更新时间

	private int isForceUpdate; //是否强制更新

	private String remark; //备注

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
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

	public int getIsForceUpdate() {
		return isForceUpdate;
	}

	public void setIsForceUpdate(int isForceUpdate) {
		this.isForceUpdate = isForceUpdate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
