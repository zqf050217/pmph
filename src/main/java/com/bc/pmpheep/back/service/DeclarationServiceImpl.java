/**
 *
 */
package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.bo.DeclarationEtcBO;
import com.bc.pmpheep.back.dao.DecAcadeDao;
import com.bc.pmpheep.back.dao.DecAchievementDao;
import com.bc.pmpheep.back.dao.DecCourseConstructionDao;
import com.bc.pmpheep.back.dao.DecEduExpDao;
import com.bc.pmpheep.back.dao.DecExtensionDao;
import com.bc.pmpheep.back.dao.DecLastPositionDao;
import com.bc.pmpheep.back.dao.DecNationalPlanDao;
import com.bc.pmpheep.back.dao.DecPositionDao;
import com.bc.pmpheep.back.dao.DecResearchDao;
import com.bc.pmpheep.back.dao.DecTeachExpDao;
import com.bc.pmpheep.back.dao.DecTextbookDao;
import com.bc.pmpheep.back.dao.DecWorkExpDao;
import com.bc.pmpheep.back.dao.DeclarationDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.DecAcade;
import com.bc.pmpheep.back.po.DecAchievement;
import com.bc.pmpheep.back.po.DecCourseConstruction;
import com.bc.pmpheep.back.po.DecEduExp;
import com.bc.pmpheep.back.po.DecLastPosition;
import com.bc.pmpheep.back.po.DecNationalPlan;
import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.back.po.DecResearch;
import com.bc.pmpheep.back.po.DecTeachExp;
import com.bc.pmpheep.back.po.DecTextbook;
import com.bc.pmpheep.back.po.DecWorkExp;
import com.bc.pmpheep.back.po.Declaration;
import com.bc.pmpheep.back.service.common.SystemMessageService;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.RouteUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.ApplicationVO;
import com.bc.pmpheep.back.vo.DecExtensionVO;
import com.bc.pmpheep.back.vo.DecPositionDisplayVO;
import com.bc.pmpheep.back.vo.DeclarationListVO;
import com.bc.pmpheep.back.vo.DeclarationOrDisplayVO;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * <p>
 * Title:Declaration业务层实现类
 * <p>
 *
 * @author lyc
 * @date 2017年9月24日 下午3:52:18
 */
@Service
public class DeclarationServiceImpl implements DeclarationService {

    @Autowired
    private DeclarationDao declarationDao;
    @Autowired
    private SystemMessageService systemMessageService;
    @Autowired
    private DecPositionDao decPositionDao;
    @Autowired
    private DecEduExpDao decEduExpDao;
    @Autowired
    private DecWorkExpDao decWorkExpDao;
    @Autowired
    private DecTeachExpDao decTeachExpDao;
    @Autowired
    private DecAchievementDao decAchievementDao;
    @Autowired
    private DecAcadeDao decAcadeDao;
    @Autowired
    private DecLastPositionDao decLastPositionDao;
    @Autowired
    private DecCourseConstructionDao decCourseConstructionDao;
    @Autowired
    private DecNationalPlanDao decNationalPlanDao;
    @Autowired
    private DecTextbookDao decTextbookDao;
    @Autowired
    private DecResearchDao decResearchDao;
    @Autowired
    private DecExtensionDao decExtensionDao;
    @Autowired
    private FileService fileService;

    @Override
    public Declaration addDeclaration(Declaration declaration) throws CheckedServiceException {
        if (null == declaration) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "参数为空");
        }
        if (null == declaration.getMaterialId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "教材id不能为空");
        }
        if (null == declaration.getUserId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "作家id不能为空");
        }
        declarationDao.addDeclaration(declaration);
        return declaration;
    }

    @Override
    public Integer deleteDeclarationById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "主键为空");
        }
        return declarationDao.deleteDeclarationById(id);
    }

    @Override
    public Integer updateDeclaration(Declaration declaration) throws CheckedServiceException {
        if (null == declaration.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "主键为空");
        }
        return declarationDao.updateDeclaration(declaration);
    }

    @Override
    public Declaration getDeclarationById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "主键为空");
        }
        return declarationDao.getDeclarationById(id);
    }

    @Override
    public List<Declaration> getDeclarationByMaterialId(Long materialId) throws CheckedServiceException {
        if (null == materialId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "教材id为空");
        }
        return declarationDao.getDeclarationByMaterialId(materialId);
    }

    @Override
    public PageResult<DeclarationListVO> pageDeclaration(Integer pageNumber, Integer pageSize, Long materialId,
            String textBookids, String realname, String position, String title, String orgName, String unitName,
            Integer positionType, Integer onlineProgress, Integer offlineProgress) throws CheckedServiceException {
        if (null == materialId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
                    "教材为空");
        }
        Gson gson = new Gson();
        List<Long> bookIds = gson.fromJson(textBookids, new TypeToken<ArrayList<Long>>() {
        }.getType());
        // 拼装复合参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("materialId", materialId);
        if (null != bookIds && bookIds.size() > 0) {
            map.put("bookIds", bookIds); // 书籍ids
        }
        if (StringUtil.notEmpty(realname)) {
            map.put("realname", StringUtil.toAllCheck(realname)); // 账号或者姓名
        }
        if (StringUtil.notEmpty(position)) {
            map.put("position", StringUtil.toAllCheck(position)); // 职务
        }
        if (StringUtil.notEmpty(title)) {
            map.put("title", StringUtil.toAllCheck(title)); // 职称
        }
        if (StringUtil.notEmpty(orgName)) {
            map.put("orgName", StringUtil.toAllCheck(orgName)); // 工作单位
        }
        if (StringUtil.notEmpty(unitName)) {
            map.put("unitName", StringUtil.toAllCheck(unitName)); // 申报单位
        }
        if (null != positionType && positionType != 0) {
            map.put("positionType", positionType); // 申报职位
        }
        if (null != onlineProgress && onlineProgress != 0) {
            map.put("onlineProgress", onlineProgress); // 学校审核进度
        }
        if (null != offlineProgress && offlineProgress != 0) {
            map.put("offlineProgress", offlineProgress); // 纸质表进度
        }
        // 包装参数实体
        PageParameter<Map<String, Object>> pageParameter = new PageParameter<Map<String, Object>>(pageNumber, pageSize,
                map);
        // 返回实体
        PageResult<DeclarationListVO> pageResult = new PageResult<>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 获取总数
        Integer total = declarationDao.listDeclarationTotal(pageParameter);
        if (null != total && total > 0) {
            List<DeclarationListVO> rows = declarationDao.listDeclaration(pageParameter);
            pageResult.setRows(rows);
        }
        pageResult.setTotal(total);
        return pageResult;
    }

    @Override
    public Declaration confirmPaperList(Long id, Integer offlineProgress, Long materialId)
            throws CheckedServiceException, IOException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "主键不能为空!");
        }
        if (ObjectUtil.isNull(offlineProgress)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "确认收到纸质表不能为空!");
        }
        // 获取当前作家用户申报信息
        Declaration declarationCon = declarationDao.getDeclarationById(id);
        declarationCon.setMaterialId(materialId);
        declarationCon.setOfflineProgress(offlineProgress);
        declarationDao.updateDeclaration(declarationCon);
        systemMessageService.sendWhenReceiptAudit(declarationCon.getId(), true); // 发送系统消息
        return declarationCon;
    }

    @Override
    public Declaration onlineProgress(Long id, Integer onlineProgress, Long materialId)
            throws CheckedServiceException, IOException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "主键不能为空!");
        }
        if (ObjectUtil.isNull(onlineProgress)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "审核进度不能为空!");
        }
        // 获取当前作家用户申报信息
        Declaration declarationCon = declarationDao.getDeclarationById(id);
        if (2 == onlineProgress.intValue()) { // 获取审核进度是2则被退回
            List<DecPosition> decPosition = decPositionDao.listDecPositions(id);
            for (DecPosition decPositions : decPosition) {
                Integer chosenPosition = decPositions.getChosenPosition();
                if (null != chosenPosition && chosenPosition.intValue() > 0) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                            CheckedExceptionResult.NULL_PARAM, "已遴选职务，不可退回给个人!");
                }
            }
            declarationCon.setOnlineProgress(onlineProgress);
            declarationDao.updateDeclaration(declarationCon);
            systemMessageService.sendWhenDeclarationFormAudit(declarationCon.getId(), false); // 发送系统消息
        } else if (3 == onlineProgress.intValue()) { // 获取审核进度是3则通过
            declarationCon.setOnlineProgress(onlineProgress);
            declarationDao.updateDeclaration(declarationCon);
            systemMessageService.sendWhenDeclarationFormAudit(declarationCon.getId(), true); // 发送系统消息
        }
        return declarationCon;
    }

    @Override
    public ApplicationVO exportExcel(Long declarationId) {
        ApplicationVO applicationVO = new ApplicationVO();
        List<DecPositionDisplayVO> decPositionList = decPositionDao.listDecPositionsOrBook(declarationId);
        for (DecPositionDisplayVO decPositions : decPositionList) {
            String syllabusId = decPositions.getSyllabusId();
            if (StringUtil.notEmpty(syllabusId)) {
                String syllabusIds = RouteUtil.MONGODB_FILE + syllabusId; // 下载路径
                decPositions.setSyllabusId(syllabusIds);
            }
            switch (decPositions.getPresetPosition()) {
                case 1:
                    decPositions.setShowPosition("编委");
                    break;
                case 2:
                    decPositions.setShowPosition("副主编");
                    break;
                case 3:
                    decPositions.setShowPosition("副主编,编委");
                    break;
                case 4:
                    decPositions.setShowPosition("主编");
                    break;
                case 5:
                    decPositions.setShowPosition("主编,编委");
                    break;
                case 6:
                    decPositions.setShowPosition("主编,副主编");
                    break;
                case 7:
                    decPositions.setShowPosition("主编,副主编,编委");
                    break;
                case 8:
                    decPositions.setShowPosition("数字编委");
                    break;
                case 9:
                    decPositions.setShowPosition("编委,数字编委");
                    break;
                case 10:
                    decPositions.setShowPosition("副主编,数字编委");
                    break;
                case 11:
                    decPositions.setShowPosition("副主编,编委,数字编委");
                    break;
                case 12:
                    decPositions.setShowPosition("主编,数字编委");
                    break;
                case 13:
                    decPositions.setShowPosition("主编,编委,数字编委");
                    break;
                case 14:
                    decPositions.setShowPosition("主编,副主编,数字编委");
                    break;
                case 15:
                    decPositions.setShowPosition("主编,副主编,编委,数字编委");
                    break;
                default:
                    break;
            }
            if (decPositions.getChosenPosition() != 0) {
                switch (decPositions.getChosenPosition()) {
                    case 1:
                        decPositions.setShowChosenPosition("编委");
                        break;
                    case 2:
                        decPositions.setShowChosenPosition("副主编");
                        break;
                    case 3:
                        decPositions.setShowChosenPosition("副主编,编委");
                        break;
                    case 4:
                        decPositions.setShowChosenPosition("主编");
                        break;
                    case 5:
                        decPositions.setShowChosenPosition("主编,编委");
                        break;
                    case 6:
                        decPositions.setShowChosenPosition("主编,副主编");
                        break;
                    case 7:
                        decPositions.setShowChosenPosition("主编,副主编,编委");
                        break;
                    case 8:
                        decPositions.setShowChosenPosition("数字编委");
                        break;
                    case 9:
                        decPositions.setShowChosenPosition("编委,数字编委");
                        break;
                    case 10:
                        decPositions.setShowChosenPosition("副主编,数字编委");
                        break;
                    case 11:
                        decPositions.setShowChosenPosition("副主编,编委,数字编委");
                        break;
                    case 12:
                        decPositions.setShowChosenPosition("主编,数字编委");
                        break;
                    case 13:
                        decPositions.setShowChosenPosition("主编,编委,数字编委");
                        break;
                    case 14:
                        decPositions.setShowChosenPosition("主编,副主编,数字编委");
                        break;
                    case 15:
                        decPositions.setShowChosenPosition("主编,副主编,编委,数字编委");
                        break;
                    default:
                        break;
                }
            }
        }
        // 专家信息
        DeclarationOrDisplayVO declaration = declarationDao.getDeclarationByIdOrOrgName(declarationId);
        // 学习经历
        List<DecEduExp> decEduExpList = decEduExpDao.getListDecEduExpByDeclarationId(declarationId);
        // 工作经历
        List<DecWorkExp> decWorkExpList = decWorkExpDao.getListDecWorkExpByDeclarationId(declarationId);
        // 教学经历
        List<DecTeachExp> decTeachExpList = decTeachExpDao.getListDecTeachExpByDeclarationId(declarationId);
        // 个人成就
        DecAchievement decAchievement = decAchievementDao.getDecAchievementByDeclarationId(declarationId);
        // 兼职学术
        List<DecAcade> decAcadeList = decAcadeDao.getListDecAcadeByDeclarationId(declarationId);
        // 上套教材
        List<DecLastPosition> decLastPositionList = decLastPositionDao
                .getListDecLastPositionByDeclarationId(declarationId);
        // 精品课程建设情况
        List<DecCourseConstruction> decCourseConstruction = decCourseConstructionDao
                .getDecCourseConstructionByDeclarationId(declarationId);
        // 主编国家级规划
        List<DecNationalPlan> decNationalPlanList = decNationalPlanDao
                .getListDecNationalPlanByDeclarationId(declarationId);
        // 教材编写
        List<DecTextbook> decTextbookList = decTextbookDao.getListDecTextbookByDeclarationId(declarationId);
        // 作家科研
        List<DecResearch> decResearchList = decResearchDao.getListDecResearchByDeclarationId(declarationId);
        // 作家扩展项
        List<DecExtensionVO> decExtensionList = decExtensionDao.getListDecExtensionByDeclarationId(declarationId);
        // 把查询出来的信息添加进applicationVO
        applicationVO.setDecPositionList(decPositionList);
        applicationVO.setDeclaration(declaration);
        applicationVO.setDecEduExpList(decEduExpList);
        applicationVO.setDecWorkExpList(decWorkExpList);
        applicationVO.setDecTeachExpList(decTeachExpList);
        applicationVO.setDecAchievement(decAchievement);
        applicationVO.setDecAcadeList(decAcadeList);
        applicationVO.setDecLastPositionList(decLastPositionList);
        applicationVO.setDecCourseConstruction(decCourseConstruction);
        applicationVO.setDecNationalPlanList(decNationalPlanList);
        applicationVO.setDecTextbookList(decTextbookList);
        applicationVO.setDecResearchList(decResearchList);
        applicationVO.setDecExtensionList(decExtensionList);
        return applicationVO;
    }

    @Override
    public List<DeclarationEtcBO> getDeclarationEtcBOs(Long materialId) {
        List<DeclarationEtcBO> declarationEtcBOs = new ArrayList<>(20);
        List<Declaration> declarations = declarationDao.getDeclarationByMaterialId(materialId);
        if (CollectionUtil.isEmpty(declarations)) {
            return null;
        }
        int count = 1;
        for (Declaration declaration : declarations) {
            // 学习经历
            ArrayList<DecEduExp> decEduExps = (ArrayList<DecEduExp>) decEduExpDao
                    .getListDecEduExpByDeclarationId(declaration.getId());
            // 工作经历
            ArrayList<DecWorkExp> decWorkExps = (ArrayList<DecWorkExp>) decWorkExpDao
                    .getListDecWorkExpByDeclarationId(declaration.getId());
            // 教学经历
            ArrayList<DecTeachExp> decTeachExps = (ArrayList<DecTeachExp>) decTeachExpDao
                    .getListDecTeachExpByDeclarationId(declaration.getId());
            // 兼职学术
            ArrayList<DecAcade> decAcades = (ArrayList<DecAcade>) decAcadeDao
                    .getListDecAcadeByDeclarationId(declaration.getId());
            // 上套教材
            ArrayList<DecLastPosition> decLastPositions = (ArrayList<DecLastPosition>) decLastPositionDao
                    .getListDecLastPositionByDeclarationId(declaration.getId());
            // 精品课程建设情况
            ArrayList<DecCourseConstruction> decCourseConstructions = (ArrayList<DecCourseConstruction>) decCourseConstructionDao
                    .getDecCourseConstructionByDeclarationId(declaration.getId());
            // 主编国家级规划
            ArrayList<DecNationalPlan> decNationalPlans = (ArrayList<DecNationalPlan>) decNationalPlanDao
                    .getListDecNationalPlanByDeclarationId(declaration.getId());
            // 教材编写
            ArrayList<DecTextbook> decTextbooks = (ArrayList<DecTextbook>) decTextbookDao
                    .getListDecTextbookByDeclarationId(declaration.getId());
            // 作家科研
            ArrayList<DecResearch> decResearchs = (ArrayList<DecResearch>) decResearchDao
                    .getListDecResearchByDeclarationId(declaration.getId());
            /*
			 * ArrayList<DecAchievement> decAchievements = (ArrayList<DecAchievement>)
			 * decAchievementDao .getDecAchievementByDeclarationId(declaration.getId());
             */
            DeclarationEtcBO declarationEtcBO = new DeclarationEtcBO();
            declarationEtcBO.setRealname("欧阳望月".concat(String.valueOf(count)));
            declarationEtcBO.setUsername("Smith");
            declarationEtcBO.setTextbookName("人体解剖学与组织胚胎学");
            declarationEtcBO.setPresetPosition("副主编");
            declarationEtcBO.setChosenOrgName("人民卫生出版社");
            declarationEtcBO.setSex("女");
            declarationEtcBO.setBirthday("1975年11月22日");
            declarationEtcBO.setAddress("浙江省金华市婺城区婺州街1188号");
            declarationEtcBO.setExperience(23);
            declarationEtcBO.setOrgName("金华职业技术学院医学院");
            declarationEtcBO.setPosition("教师");
            declarationEtcBO.setTitle("教授");
            declarationEtcBO.setPostcode("321017");
            declarationEtcBO.setHandphone("13857989881");
            declarationEtcBO.setEmail("test10001test@163.com");
            declarationEtcBO.setFax("01065930013");
            declarationEtcBO.setTelephone("010-65930013");
            declarationEtcBO.setDecEduExps(decEduExps);
            declarationEtcBO.setDecWorkExps(decWorkExps);
            declarationEtcBO.setDecTeachExps(decTeachExps);
            declarationEtcBO.setDecAcades(decAcades);
            declarationEtcBO.setDecLastPositions(decLastPositions);
            declarationEtcBO.setDecCourseConstructions(decCourseConstructions);
            declarationEtcBO.setDecNationalPlans(decNationalPlans);
            declarationEtcBO.setDecTextbooks(decTextbooks);
            declarationEtcBO.setDecResearchs(decResearchs);
            // declarationEtcBO.setDecAchievement(decAchievements.get(0));
            declarationEtcBOs.add(declarationEtcBO);
            count++;
            if (count > 21) {
                break;
            }
        }
        return declarationEtcBOs;
    }

    @Override
    public List<DeclarationEtcBO> declarationEtcBO(Long materialId, String textBookids, String realname,
            String position, String title, String orgName, String unitName, Integer positionType,
            Integer onlineProgress, Integer offlineProgress)
            throws CheckedServiceException, IllegalArgumentException, IllegalAccessException {
        long startTime = System.currentTimeMillis();// 获取当前时间
        List<DeclarationEtcBO> declarationEtcBOs = new ArrayList<>();
        Gson gson = new Gson();
        List<Long> bookIds = gson.fromJson(textBookids, new TypeToken<ArrayList<Long>>() {
        }.getType());
        List<DeclarationOrDisplayVO> declarationOrDisplayVOs = declarationDao.getDeclarationOrDisplayVOByMaterialId(
                materialId, bookIds, realname, position, title, orgName, unitName, positionType, onlineProgress,
                offlineProgress);
        List<Long> decIds = new ArrayList<>();
        for (DeclarationOrDisplayVO declarationOrDisplayVO : declarationOrDisplayVOs) {
            decIds.add(declarationOrDisplayVO.getId());
        }
        // 学习经历
        ArrayList<DecEduExp> decEduExps = (ArrayList<DecEduExp>) decEduExpDao.getListDecEduExpByDeclarationIds(decIds);
        // 工作经历
        ArrayList<DecWorkExp> decWorkExps = (ArrayList<DecWorkExp>) decWorkExpDao
                .getListDecWorkExpByDeclarationIds(decIds);
        // 教学经历
        ArrayList<DecTeachExp> decTeachExps = (ArrayList<DecTeachExp>) decTeachExpDao
                .getListDecTeachExpByDeclarationIds(decIds);
        // 兼职学术
        ArrayList<DecAcade> decAcades = (ArrayList<DecAcade>) decAcadeDao.getListDecAcadeByDeclarationIds(decIds);
        // 个人成就
        ArrayList<DecAchievement> decAchievements = (ArrayList<DecAchievement>) decAchievementDao
                .getDecAchievementByDeclarationIds(decIds);
        // 上套教材
        ArrayList<DecLastPosition> decLastPositions = (ArrayList<DecLastPosition>) decLastPositionDao
                .getListDecLastPositionByDeclarationIds(decIds);
        // 精品课程建设情况
        ArrayList<DecCourseConstruction> decCourseConstructions = (ArrayList<DecCourseConstruction>) decCourseConstructionDao
                .getDecCourseConstructionByDeclarationIds(decIds);
        // 主编国家级规划
        ArrayList<DecNationalPlan> decNationalPlans = (ArrayList<DecNationalPlan>) decNationalPlanDao
                .getListDecNationalPlanByDeclarationIds(decIds);
        // 教材编写
        ArrayList<DecTextbook> decTextbooks = (ArrayList<DecTextbook>) decTextbookDao
                .getListDecTextbookByDeclarationIds(decIds);
        // 作家科研
        ArrayList<DecResearch> decResearchs = (ArrayList<DecResearch>) decResearchDao
                .getListDecResearchByDeclarationIds(decIds);
        for (DeclarationOrDisplayVO declarationOrDisplayVO : declarationOrDisplayVOs) {
            String strOnlineProgress = "";
            String strOfflineProgress = "";
            String sex = "";
            switch (declarationOrDisplayVO.getOnlineProgress()) {
                case 0:
                    strOnlineProgress = "未提交";
                    break;
                case 1:
                    strOnlineProgress = "已提交";
                    break;
                case 2:
                    strOnlineProgress = "被退回";
                    break;
                case 3:
                    strOnlineProgress = "已通过";
                    break;
                default:
                    break;
            }
            switch (declarationOrDisplayVO.getSex()) {
                case 0:
                    sex = "保密";
                    break;
                case 1:
                    sex = "男";
                    break;
                case 2:
                    sex = "女";
                    break;
                default:
                    break;
            }
            switch (declarationOrDisplayVO.getOfflineProgress()) {
                case 0:
                    strOfflineProgress = "未收到";
                    break;
                case 1:
                    strOfflineProgress = "被退回";
                    break;
                case 2:
                    strOfflineProgress = "已收到";
                    break;
                default:
                    break;
            }
            String birthday = "";
            if (null != declarationOrDisplayVO.getBirthday()) {
                birthday = DateUtil.date2Str(declarationOrDisplayVO.getBirthday(), "yyyy-MM-dd");
            }
            if (null == declarationOrDisplayVO.getPosition() || "".equals(declarationOrDisplayVO.getPosition())) {
                declarationOrDisplayVO.setPosition("无");
            }
            if (StringUtil.isEmpty(declarationOrDisplayVO.getTextbookName())) {
                declarationOrDisplayVO.setTextbookName("");
            }
            if (StringUtil.isEmpty(declarationOrDisplayVO.getPresetPosition())) {
                declarationOrDisplayVO.setPresetPosition("");
            }
            // 学习经历
            List<DecEduExp> decEduExp = new ArrayList<>();
            for (DecEduExp exp : decEduExps) {
                if (exp.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decEduExp.add(exp);
                }
            }
            // 工作经历
            List<DecWorkExp> decWorkExp = new ArrayList<>();
            for (DecWorkExp workExp : decWorkExps) {
                if (workExp.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decWorkExp.add(workExp);
                }
            }
            // 教学经历
            List<DecTeachExp> decTeachExp = new ArrayList<>();
            for (DecTeachExp teachExp : decTeachExps) {
                if (teachExp.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decTeachExp.add(teachExp);
                }
            }
            // 兼职学术
            List<DecAcade> decAcade = new ArrayList<>();
            for (DecAcade acade : decAcades) {
                if (acade.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decAcade.add(acade);
                }
            }
            // 个人成就
            DecAchievement decAchievement = new DecAchievement();
            for (DecAchievement achievement : decAchievements) {
                if (achievement.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decAchievement = achievement;
                }
            }
            // 上套教材
            List<DecLastPosition> decLastPosition = new ArrayList<>();
            for (DecLastPosition lastPosition : decLastPositions) {
                if (lastPosition.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decLastPosition.add(lastPosition);
                }
            }
            // 精品课程建设情况
            List<DecCourseConstruction> decCourseConstruction = new ArrayList<>();
            for (DecCourseConstruction construction : decCourseConstructions) {
                if (construction.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decCourseConstruction.add(construction);
                }
            }
            // 主编国家级规划
            List<DecNationalPlan> decNationalPlan = new ArrayList<>();
            for (DecNationalPlan plan : decNationalPlans) {
                if (plan.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decNationalPlan.add(plan);
                }
            }
            // 教材编写
            List<DecTextbook> decTextbook = new ArrayList<>();
            for (DecTextbook textbook : decTextbooks) {
                if (textbook.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decTextbook.add(textbook);
                }
            }
            // 作家科研
            List<DecResearch> decResearch = new ArrayList<>();
            for (DecResearch research : decResearchs) {
                if (research.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decResearch.add(research);
                }
            }

            DeclarationEtcBO declarationEtcBO = new DeclarationEtcBO(declarationOrDisplayVO.getTextbookName(),
                    declarationOrDisplayVO.getPresetPosition(), declarationOrDisplayVO.getRealname(),
                    declarationOrDisplayVO.getUsername(), sex, birthday, declarationOrDisplayVO.getExperience(),
                    declarationOrDisplayVO.getOrgName(), declarationOrDisplayVO.getPosition(),
                    declarationOrDisplayVO.getTitle(), declarationOrDisplayVO.getAddress(),
                    declarationOrDisplayVO.getPostcode(), declarationOrDisplayVO.getTelephone(),
                    declarationOrDisplayVO.getFax(), declarationOrDisplayVO.getHandphone(),
                    declarationOrDisplayVO.getEmail(), strOnlineProgress, strOfflineProgress,
                    declarationOrDisplayVO.getOrgNameOne(), (ArrayList<DecEduExp>) decEduExp,
                    (ArrayList<DecWorkExp>) decWorkExp, (ArrayList<DecTeachExp>) decTeachExp,
                    (ArrayList<DecAcade>) decAcade, (ArrayList<DecLastPosition>) decLastPosition,
                    (ArrayList<DecCourseConstruction>) decCourseConstruction,
                    (ArrayList<DecNationalPlan>) decNationalPlan, (ArrayList<DecTextbook>) decTextbook,
                    (ArrayList<DecResearch>) decResearch, decAchievement);
            declarationEtcBOs.add(declarationEtcBO);
        }
        long endTime = System.currentTimeMillis();
        System.err.println("------------------------------------------");
        System.err.println("查询时间：" + (endTime - startTime) + "ms， 一共" + declarationEtcBOs.size() + "条数据");
        return declarationEtcBOs;
    }

    @Override
    public List<Declaration> getDeclarationByIds(List<Long> ids) throws CheckedServiceException {
        if (null == ids || ids.isEmpty()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM, "参数为空!");
        }
        List<Declaration> declarations = declarationDao.getDeclarationByIds(ids);
        return declarations;
    }

    @Override
    public Declaration getDeclarationByMaterialIdAndUserId(Long materialId, Long userId) throws CheckedServiceException {
        if (null == materialId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "教材id为空");
        }
        if (null == userId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "作家id为空");
        }
        return declarationDao.getDeclarationByMaterialIdAndUserId(materialId, userId);
    }
}
