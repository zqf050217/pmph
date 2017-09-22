package com.bc.pmpheep.back.po;



import org.apache.ibatis.type.Alias;

/**
 * 
 * <p>Title:作家学习经历表实体类<p>
 * <p>Description:作家学习经历信息<p>
 * @author lyc
 * @date 2017年9月22日 上午9:51:38
 */

@SuppressWarnings("serial")
@Alias("DecEduExp")
public class DecEduExp implements java.io.Serializable {

	// Fields

	private Long id;
	private Long declarationId;
	private String schoolName;
	private String major;
	private String degree;
	private String note;
	private String dateBegin;
	private String dateEnd;
	private Integer sort;

	// Constructors

	/** default constructor */
	public DecEduExp() {
	}

	

	public DecEduExp(Long id) {
		super();
		this.id = id;
	}



	/** full constructor */
	public DecEduExp(Long declarationId, String schoolName, String major,
			String degree, String note, String dateBegin, String dateEnd,
			Integer sort) {
		this.declarationId = declarationId;
		this.schoolName = schoolName;
		this.major = major;
		this.degree = degree;
		this.note = note;
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
		this.sort = sort;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDeclarationId() {
		return declarationId;
	}

	public void setDeclarationId(Long declarationId) {
		this.declarationId = declarationId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(String dateBegin) {
		this.dateBegin = dateBegin;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return " {id:" + id + ", declarationId:" + declarationId
				+ ", schoolName:" + schoolName + ", major:" + major
				+ ", degree:" + degree + ", note:" + note + ", dateBegin:"
				+ dateBegin + ", dateEnd:" + dateEnd + ", sort:" + sort + "}";
	}

	

}