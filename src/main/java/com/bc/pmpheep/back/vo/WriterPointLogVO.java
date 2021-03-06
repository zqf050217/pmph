package com.bc.pmpheep.back.vo;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

/**
 * 积分记录表实体类VO
 * @author tyc
 * @date 2017年12月28日 下午15:19:54
 */
@SuppressWarnings("serial")
@Alias("WriterPointLogVO")
public class WriterPointLogVO implements java.io.Serializable{

	//主键
	private Long id;
	//用户id
	private Long userId;
	// 用户名
    private String username;
    // 真实姓名
    private String realname;
	//规则id
	private Long ruleId;
	//规则名称
	private String ruleName;
	//积分变化(可为负数)
	private Integer point;
	//创建时间
	private Date gmtCreate;
    // 开始时间
    private String startTime;
    // 结束时间
    private String endTime;
    // 当前总积分
    private Integer total;
    // 消费积分合计
    private Integer loss;
	//兑换三方积分
	private Integer exchangePoint;
	//条件分页总条数分页查询
    private Integer count;
    //页面查询条件（状态）
    private Integer status;
	
	//构造器
	public WriterPointLogVO(){
	}
	
	public WriterPointLogVO(Long id){
		super();
		this.id = id;
	}
	
	public WriterPointLogVO(Long id, Long userId, Long ruleId, Integer point){
		super();
		this.id = id;
		this.userId = userId;
		this.ruleId = ruleId;
		this.point = point;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getLoss() {
		return loss;
	}

	public void setLoss(Integer loss) {
		this.loss = loss;
	}

	public Integer getExchangePoint() {
		return exchangePoint;
	}

	public void setExchangePoint(Integer exchangePoint) {
		this.exchangePoint = exchangePoint;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	@Override
	public String toString() {
		return "WriterPointLogVO [id=" + id + ", userId=" + userId
				+ ", username=" + username + ", realname=" + realname
				+ ", ruleId=" + ruleId + ", ruleName=" + ruleName + ", point="
				+ point + ", gmtCreate=" + gmtCreate + ", startTime="
				+ startTime + ", endTime=" + endTime + ", total=" + total
				+ ", loss=" + loss + ", exchangePoint=" + exchangePoint
				+ ", count=" + count + ", status=" + status + "]";
	}
}
