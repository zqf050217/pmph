/**
 * 
 */
package com.bc.pmpheep.back.vo;

import java.sql.Timestamp;

/**
 * <p>
 * Title:小组文件共享
 * <p>
 * 
 * @author lyc
 * @date 2017年9月29日 下午3:55:53
 */
public class PmphGroupFileVO {
	// 主键
	private Long id;
	//小组id
	private Long groupId;
	//文件id
	private String fileId;
	//文件名
	private String fileName;
	//上传时间
	private Timestamp gmtCreate;
	//上传者id
	private Long memberId;
	//上传者小组内名字
	private String memberName;
	//上传小组数
	private Integer groupCount;
	//下载次数
	private Integer download;
	
	public PmphGroupFileVO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Integer getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(Integer groupCount) {
		this.groupCount = groupCount;
	}

	public Integer getDownload() {
		return download;
	}

	public void setDownload(Integer download) {
		this.download = download;
	}

	@Override
	public String toString() {
		return " {id:" + id + ", groupId:" + groupId + ", fileId:" + fileId
				+ ", fileName:" + fileName + ", gmtCreate:" + gmtCreate
				+ ", memberId:" + memberId + ", groupCount:" + groupCount
				+ ", download:" + download + "}";
	}
	
}
