/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DecPositionDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.JsonUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.DecPositionEditorSelectionVO;
import com.bc.pmpheep.back.vo.DecPositionVO;
import com.bc.pmpheep.back.vo.DeclarationCountVO;
import com.bc.pmpheep.back.vo.DeclarationResultBookVO;
import com.bc.pmpheep.back.vo.DeclarationResultSchoolVO;
import com.bc.pmpheep.back.vo.DeclarationSituationBookResultVO;
import com.bc.pmpheep.back.vo.DeclarationSituationSchoolResultVO;
import com.bc.pmpheep.back.vo.NewDecPosition;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * <p>
 * Title:DecPositionServiceImpl
 * <p>
 * <p>
 * Description:作家职位遴选信息
 * <p>
 * 
 * @author lyc
 * @date 2017年10月9日 下午6:05:54
 */
@Service
public class DecPositionServiceImpl implements DecPositionService {

    @Autowired
    private DecPositionDao     decPositionDao;
    @Autowired
    private FileService        fileService;
    @Autowired
    private DecPositionService decPositionService;
    @Autowired
    private TextbookLogService textbookLogService;

    @Override
    public DecPosition addDecPosition(DecPosition decPosition) throws CheckedServiceException {
        if (null == decPosition) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "参数不能为空");
        }
        if (null == decPosition.getDeclarationId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "申报表id不能为空");
        }
        if (null == decPosition.getTextbookId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "书籍id不能为空");
        }
        if (null == decPosition.getPresetPosition()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "申报职务不能为空");
        }
        decPositionDao.addDecPosition(decPosition);
        return decPosition;
    }

    @Override
    public Integer deleteDecPosition(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "id不能为空");
        }
        return decPositionDao.deleteDecPosition(id);
    }

    @Override
    public Integer updateDecPosition(DecPosition decPosition) throws CheckedServiceException {
        if (null == decPosition.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "id不能为空");
        }
        return decPositionDao.updateDecPosition(decPosition);
    }

    @Override
    public DecPosition getDecPositionById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "id不能为空");
        }
        return decPositionDao.getDecPositionById(id);
    }

    @Override
    public List<DecPosition> listDecPositionsByTextbookIdAndOrgid(List<Long> textBookIds, Long orgId)
    throws CheckedServiceException {
        if (null == textBookIds || textBookIds.size() == 0 || null == orgId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        for (Long bookId : textBookIds) {
            if (null == bookId) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                                  CheckedExceptionResult.NULL_PARAM, "书籍参数为空");
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", textBookIds); // 书籍ids
        map.put("orgId", orgId); // 网址类型机构
        return decPositionDao.listDecPositionsByTextbookIdAndOrgid(map);
    }

    @Override
    public List<DecPosition> listDecPositions(Long declarationId) throws CheckedServiceException {
        if (null == declarationId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "申报表id不能为空");
        }
        return decPositionDao.listDecPositions(declarationId);
    }

    @Override
    public List<DecPosition> listDecPositionsByTextbookId(Long textbookId)
    throws CheckedServiceException {
        if (null == textbookId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "书籍id不能为空");
        }
        return decPositionDao.listDecPositionsByTextbookId(textbookId);
    }

    @Override
    public List<DecPosition> listChosenDecPositionsByTextbookId(Long textbookId)
    throws CheckedServiceException {
        if (null == textbookId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "书籍id不能为空");
        }
        return decPositionDao.listChosenDecPositionsByTextbookId(textbookId);
    }

    @Override
    public List<Long> listDecPositionsByTextbookIds(String[] textbookIds)
    throws CheckedServiceException {
        if (0 == textbookIds.length) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "书籍id不能为空");
        }
        return decPositionDao.listDecPositionsByTextbookIds(textbookIds);
    }

    @Override
    public long saveBooks(DecPositionVO decPositionVO) throws IOException {
        List<NewDecPosition> list = decPositionVO.getList();
        if (CollectionUtil.isEmpty(list)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "list不能为空");
        }
        List<DecPosition> istDecPositions =
        decPositionDao.listDecPositions(list.get(0).getDeclarationId());
        String newId = ",";
        for (NewDecPosition newDecPosition : list) {
            Long id = newDecPosition.getId();
            Long declarationId = newDecPosition.getDeclarationId();
            Long textbookId = newDecPosition.getTextbookId();
            Integer presetPosition = newDecPosition.getPresetPosition();
            File file = newDecPosition.getFile();
            Boolean isDigitalEditor = newDecPosition.getIsDigitalEditor();
            DecPosition decPosition = new DecPosition();
            if (null == file) {
                decPosition.setSyllabusName(null);
            } else {
                String fileName = file.getName(); // 获取原文件名字
                decPosition.setSyllabusName(fileName);
            }
            decPosition.setDeclarationId(declarationId);
            decPosition.setTextbookId(textbookId);
            decPosition.setPresetPosition(presetPosition);
            decPosition.setIsDigitalEditor(isDigitalEditor);
            decPosition.setId(id);
            if (ObjectUtil.isNull(id)) { // 保存或者修改
                decPositionDao.addDecPosition(decPosition);
                String mongoId = null;
                if (null == file) {

                } else {
                    mongoId = fileService.saveLocalFile(file, FileType.SYLLABUS, decPosition.getId());
                    if (null != mongoId) {
                        decPosition.setSyllabusId(mongoId);
                        decPositionDao.updateDecPosition(decPosition);
                    }
                }
            } else {
                decPositionDao.updateDecPosition(decPosition);
            }
            newId += decPosition.getId() + ",";
        }
        for (DecPosition decPositions : istDecPositions) {
            if (!newId.contains("," + decPositions.getId() + ",")) { // 不包含
                decPositionDao.deleteDecPosition(decPositions.getId());
            }
        }
        return list.size();
    }

    @Override
    public List<DecPositionEditorSelectionVO> listEditorSelection(Long textbookId, String realName,
    Integer presetPosition) throws CheckedServiceException {
        if (ObjectUtil.isNull(textbookId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "书籍id不能为空");
        }
        return decPositionDao.listEditorSelection(textbookId,
                                                  StringUtil.toAllCheck(realName),
                                                  presetPosition);
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Integer updateDecPositionEditorSelection(String jsonDecPosition, String sessionId)
    throws CheckedServiceException {
        if (StringUtil.isEmpty(jsonDecPosition)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "遴选职位不能为空");
        }
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        if (ObjectUtil.isNull(pmphUser.getId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        Integer count = 0;
        List<DecPosition> decPositions =
        new JsonUtil().getArrayListObjectFromStr(DecPosition.class, jsonDecPosition);// json字符串转List对象集合
        if (CollectionUtil.isEmpty(decPositions)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "遴选职位为空");
        }
        Long textbookId = decPositions.get(0).getTextbookId(); // 获取书籍id
        if (ObjectUtil.isNull(textbookId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "书籍id为空");
        }
        List<DecPosition> oldlist =
        decPositionService.listChosenDecPositionsByTextbookId(textbookId);
        Long updaterId = pmphUser.getId(); // 获取修改者id
        int userType = 1;
        if (CollectionUtil.isNotEmpty(decPositions)) {
            count = decPositionDao.updateDecPositionEditorSelection(decPositions);
        }
        if (count > 0) {
            textbookLogService.addTextbookLog(oldlist, textbookId, updaterId, userType);
        }
        return count;
    }

    @Override
    public PageResult<DeclarationSituationSchoolResultVO> listDeclarationSituationSchoolResultVOs(
    PageParameter<DeclarationSituationSchoolResultVO> pageParameter) throws CheckedServiceException {
        if (ObjectUtil.isNull(pageParameter.getParameter().getMaterialId())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "教材id不能为空");
        }
        // 如果机构名称不为空，则为模糊查询
        String schoolName = pageParameter.getParameter().getSchoolName();
        if (StringUtil.notEmpty(schoolName)) {
            pageParameter.getParameter().setSchoolName(schoolName);
        }
        PageResult<DeclarationSituationSchoolResultVO> pageResult =
        new PageResult<DeclarationSituationSchoolResultVO>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 得到申报单位的总数
        int total = decPositionDao.getSchoolCount(pageParameter.getParameter().getMaterialId());
        if (total > 0) {
            List<DeclarationSituationSchoolResultVO> declarationSituationSchoolResultVOs =
            decPositionDao.getSchoolResult(pageParameter);
            List<DeclarationSituationSchoolResultVO> list = new ArrayList<>();
            for (DeclarationSituationSchoolResultVO declarationSituationSchoolResultVO : declarationSituationSchoolResultVOs) {
                // 计算申报人数
                Integer presetPersons =
                declarationSituationSchoolResultVO.getPresetPositionEditor()
                + declarationSituationSchoolResultVO.getPresetPositionSubeditor()
                + declarationSituationSchoolResultVO.getPresetPositionEditorial();
                // 计算当选人数
                Integer chosenPersons =
                declarationSituationSchoolResultVO.getChosenPositionEditor()
                + declarationSituationSchoolResultVO.getChosenPositionSubeditor()
                + declarationSituationSchoolResultVO.getChosenPositionEditorial()
                + declarationSituationSchoolResultVO.getIsDigitalEditor();
                declarationSituationSchoolResultVO.setPresetPersons(presetPersons);
                declarationSituationSchoolResultVO.setChosenPersons(chosenPersons);
                list.add(declarationSituationSchoolResultVO);
            }
            pageResult.setRows(list);
            pageResult.setTotal(total);
        }
        return pageResult;
    }

    @Override
    public DeclarationCountVO getDeclarationCountVO(Long materialId) throws CheckedServiceException {
        DeclarationCountVO declarationCountVO = new DeclarationCountVO();
        Integer schoolDeclarationCount = decPositionDao.getSchoolDeclarationCount(materialId);
        Integer schoolDeclarationAverage = 0;
        if (decPositionDao.getSchoolCount(materialId) > 0){
        	schoolDeclarationAverage = schoolDeclarationCount/ decPositionDao.getSchoolCount(materialId);
        }      
        Integer editorCount = decPositionDao.getEditorCount(materialId);
        Integer subEditorCount = decPositionDao.getSubEditorCount(materialId);
        Integer editorialCount = decPositionDao.getEditorialCount(materialId);
        declarationCountVO.setSchoolDeclarationCount(schoolDeclarationCount);
        declarationCountVO.setSchoolDeclarationAverage(schoolDeclarationAverage);
        declarationCountVO.setEditorCount(editorCount);
        declarationCountVO.setSubEditorCount(subEditorCount);
        declarationCountVO.setEditorialCount(editorialCount);
        return declarationCountVO;
    }

	@Override
	public PageResult<DeclarationSituationBookResultVO> listDeclarationSituationBookResultVOs(
			PageParameter<DeclarationSituationBookResultVO> pageParameter)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(pageParameter.getParameter().getMaterialId())){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.NULL_PARAM, "教材id不能为空");
		}
		String bookName = pageParameter.getParameter().getBookName();
		if (StringUtil.notEmpty(bookName)){
			pageParameter.getParameter().setBookName(bookName);
		}
		PageResult<DeclarationSituationBookResultVO> pageResult = 
				new PageResult<DeclarationSituationBookResultVO>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		int total = decPositionDao.getBooks(pageParameter.getParameter().getMaterialId());
		if (total > 0){
			List<DeclarationSituationBookResultVO> declarationSituationBookResultVOs = 
					decPositionDao.getBookResult(pageParameter);
			List<DeclarationSituationBookResultVO> list = new ArrayList<>();
			for (DeclarationSituationBookResultVO declarationSituationBookResultVO : declarationSituationBookResultVOs){
				//计算申报人数和当选人数
				Integer presetPersons = declarationSituationBookResultVO.getPresetPositionEditor()
						+declarationSituationBookResultVO.getPresetPositionSubeditor()
						+declarationSituationBookResultVO.getPresetPositionEditorial();
				Integer chosenPersons = declarationSituationBookResultVO.getChosenPositionEditor()
						+declarationSituationBookResultVO.getChosenPositionSubeditor()
						+declarationSituationBookResultVO.getChosenPositionEditorial()
						+declarationSituationBookResultVO.getIsDigitalEditor();
				declarationSituationBookResultVO.setPresetPersons(presetPersons);
				declarationSituationBookResultVO.setChosenPersons(chosenPersons);
				list.add(declarationSituationBookResultVO);
			}
			pageResult.setRows(list);
			pageResult.setTotal(total);
		}
		return pageResult;
	}

	@Override
	public PageResult<DeclarationResultSchoolVO> listDeclarationResultSchoolVOs(
			PageParameter<DeclarationResultSchoolVO> pageParameter)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(pageParameter.getParameter().getMaterialId())){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.NULL_PARAM, "教材id不能为空");
		}
		String schoolName = pageParameter.getParameter().getSchoolName();
		if (StringUtil.notEmpty(schoolName)){
			pageParameter.getParameter().setSchoolName(schoolName);
		}
		PageResult<DeclarationResultSchoolVO> pageResult = new PageResult<DeclarationResultSchoolVO>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		int total = decPositionDao.getSchoolCount(pageParameter.getParameter().getMaterialId());
		if (total > 0){
			List<DeclarationResultSchoolVO> list = decPositionDao.getSchoolList(pageParameter);	
			pageResult.setRows(list);
			pageResult.setTotal(total);
		}
		return pageResult;
	}

	@Override
	public PageResult<DeclarationResultBookVO> listDeclarationResultBookVOs(
			PageParameter<DeclarationResultBookVO> pageParameter)
			throws CheckedServiceException {
		if (ObjectUtil.isNull(pageParameter.getParameter().getMaterialId())){
			throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
					CheckedExceptionResult.NULL_PARAM, "教材id不能为空");
		}
		String bookName = pageParameter.getParameter().getBookName();
		if (StringUtil.notEmpty(bookName)){
			pageParameter.getParameter().setBookName(bookName);
		}
		PageResult<DeclarationResultBookVO> pageResult = new PageResult<DeclarationResultBookVO>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		int total = decPositionDao.getBooks(pageParameter.getParameter().getMaterialId());
		if (total > 0){
			List<DeclarationResultBookVO> list = decPositionDao.getBookList(pageParameter);
			pageResult.setTotal(total);
			pageResult.setRows(list);
		}
		return pageResult;
	}
}
