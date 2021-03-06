package com.bc.pmpheep.back.po;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * PmphRole（社内用户角色表）实体类 对应数据库 pmph_role
 * 
 * @author 曾庆峰
 * 
 */
@SuppressWarnings("serial")
@Alias("PmphRole")
public class PmphRole implements Serializable {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 是否禁用
	 */
	private boolean isDisabled;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 显示顺序
	 */
	private Integer sort;
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	/**
	 * 修改时间
	 */
	private Date gmtUpdate;

	// 角色对应权限
	List<Long> pmphRolePermissionChild;
	// 角色对应的教材权限
	private String materialPermission;

	public PmphRole() {
	}

	/**
	 * @param id
	 * @param roleName
	 * @param isDisabled
	 * @param note
	 * @param sort
	 * @param gmtCreate
	 * @param gmtUpdate
	 */
	public PmphRole(String roleName, boolean isDisabled, String note, Integer sort, Date gmtCreate, Date gmtUpdate) {
		this.roleName = roleName;
		this.isDisabled = isDisabled;
		this.note = note;
		this.sort = sort;
		this.gmtCreate = gmtCreate;
		this.gmtUpdate = gmtUpdate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtUpdate() {
		return gmtUpdate;
	}

	public void setGmtUpdate(Date gmtUpdate) {
		this.gmtUpdate = gmtUpdate;
	}

	/**
	 * @return the pmphRolePermissionChild
	 */
	public List<Long> getPmphRolePermissionChild() {
		return pmphRolePermissionChild;
	}

	/**
	 * @param pmphRolePermissionChild
	 *            the pmphRolePermissionChild to set
	 */
	public void setPmphRolePermissionChild(List<Long> pmphRolePermissionChild) {
		this.pmphRolePermissionChild = pmphRolePermissionChild;
	}

	public boolean isDisabled() {
		return isDisabled;
	}

	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public String getMaterialPermission() {
		return materialPermission;
	}

	public void setMaterialPermission(String materialPermission) {
		this.materialPermission = materialPermission;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", roleName:" + roleName + ", isDisabled:" + isDisabled + ", note:" + note + ", sort:"
				+ sort + ", gmtCreate:" + gmtCreate + ", gmtUpdate:" + gmtUpdate + "}";
	}

}
