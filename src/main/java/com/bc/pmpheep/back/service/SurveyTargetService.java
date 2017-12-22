package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.po.SurveyTarget;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：问卷调查发起问卷业务层
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-12-21
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
public interface SurveyTargetService {
	
	/**
     * 新增一个SurveyTarget
     * 
     * @author:tyc
     * @date:2017年12月22日下午16:42:11
     * @param SurveyTarget 实体对象
     * @return 影响行数
     */
	SurveyTarget addSurveyTarget(SurveyTarget surveyTarget) throws CheckedServiceException;

    /**
     * 删除SurveyTarget通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午16:45:02
     * @param SurveyTarget
     * @return 影响行数
     */
    Integer deleteSurveyTargetById(Long id) throws CheckedServiceException;

    /**
     * 更新一个 SurveyTarget通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午16:48:28
     * @param SurveyTarget
     * @return 影响行数
     */
    Integer updateSurveyTarget(SurveyTarget surveyTarget) throws CheckedServiceException;

    /**
     * 查找SurveyTarget通过主键id
     * 
     * @author:tyc
     * @date:2017年12月22日下午16:51:25
     * @param SurveyTarget
     * @return 影响行数
     */
    SurveyTarget getSurveyTargetById(Long id) throws CheckedServiceException;
}
