package com.bc.pmpheep.back.controller.pmphDepartment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.back.service.PmphDepartmentService;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * @author MrYang
 * @CreateDate 2017年9月26日 下午3:19:42
 *
 **/
@Controller
@RequestMapping(value = "/departments")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PmphDepartmentController {

	@Autowired
	private PmphDepartmentService pmphDepartmentService;
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "社内部门";

	/**
	 * 根据父级id获取社内机构下级所有部门 parentId为null 获取整个社内部门树（根节点为0）
	 * 
	 * @author Mryang
	 * @createDate 2017年9月26日 下午3:21:34
	 * @param parentId
	 */
	@RequestMapping(value = "/pmphdepartmenttree", method = RequestMethod.GET)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "根据父级id获取整个部门")
	@ResponseBody
	public ResponseBean pmphdepartmenttree(Long parentId) {
		return new ResponseBean(pmphDepartmentService.listPmphDepartment(parentId));
	}

	/**
	 * 新增 pmphDepartment
	 * 
	 * @author Mryang
	 * @createDate 2017年9月26日 下午3:28:24
	 * @param pmphDepartment
	 * @return 带主键的pmphDepartment
	 */
	@RequestMapping(value = "/add/department", method = RequestMethod.POST)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增部门")
	@ResponseBody
	public ResponseBean department(PmphDepartment pmphDepartment) {
		return new ResponseBean(pmphDepartmentService.addPmphDepartment(pmphDepartment));
	}

	/**
	 * 更新 pmphDepartment
	 * 
	 * @author Mryang
	 * @createDate 2017年9月26日 下午3:36:41
	 * @param pmphDepartment
	 * @return 影响行数
	 */
	@RequestMapping(value = "/update/pmphdepartment", method = RequestMethod.PUT)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "更新部门")
	@ResponseBody
	public ResponseBean pmphdepartment(PmphDepartment pmphDepartment) {
		return new ResponseBean(pmphDepartmentService.updatePmphDepartment(pmphDepartment));
	}

	/**
	 * 删除该部门以及下属部门
	 * 
	 * @author Mryang
	 * @createDate 2017年9月26日 下午3:38:14
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/pmphdepartmentbatch", method = RequestMethod.DELETE)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除部门")
	@ResponseBody
	public ResponseBean pmphdepartmentbatch(Long id) {
		return new ResponseBean(pmphDepartmentService.deletePmphDepartmentBatch(id));
	}

	/**
	 * 
	 * 
	 * 功能描述：根据部门名称模糊查询部门
	 *
	 * @param dpName
	 *            部门名称
	 * @return 查询出来的结果集
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "根据部门名称模糊查询部门")
	@RequestMapping(value = "/list/pmphdepartment", method = RequestMethod.GET)
	public ResponseBean pmphdepartment(String dpName) {
		return new ResponseBean(pmphDepartmentService.listPmphUserDepartmentByDpName(dpName));
	}
}
