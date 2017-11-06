package com.bc.pmpheep.back.controller.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.CmsContentService;
import com.bc.pmpheep.back.vo.CmsContentVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * <pre>
 * 功能描述：社外内容管理 控制器
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-11-2
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Controller
@RequestMapping(value = "/cms")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CmsContentManageController {
    @Autowired
    CmsContentService cmsContentService;

    /**
     * 
     * <pre>
     * 功能描述：分页查询条件查询《社外内容管理》列表
     * 使用示范：
     *
     * @param pageNumber 当前页
     * @param pageSize 页面数据条数
     * @param cmsContentVO
     * @param sessionId
     * @return 分页数据集
     * </pre>
     */
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean listContentManage(
    @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
    @RequestParam(name = "pageSize") Integer pageSize, CmsContentVO cmsContentVO,
    @RequestParam("sessionId") String sessionId) {
        PageParameter<CmsContentVO> pageParameter =
        new PageParameter<CmsContentVO>(pageNumber, pageSize, cmsContentVO);
        return new ResponseBean(cmsContentService.listContentManage(pageParameter, sessionId));
    }

    /**
     * 
     * <pre>
     * 功能描述：点击标题查看内容
     * 使用示范：
     *
     * @param id CmsContent_id 主键
     * @return Map<String,Object>
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/manage/content/{id}/search", method = RequestMethod.GET)
    public ResponseBean searchCmsContent(@PathVariable("id") Long id) {
        return new ResponseBean(cmsContentService.getCmsContentAndContentAndAttachmentById(id));
    }

    /**
     * 
     * <pre>
     * 功能描述：内容隐藏
     * 使用示范：
     *
     * @param id 主键ID
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/manage/content/{id}/hide", method = RequestMethod.PUT)
    public ResponseBean hideCmsContent(@PathVariable("id") Long id) {
        return new ResponseBean(cmsContentService.hideCmsContentById(id));
    }

    /**
     * 
     * <pre>
     * 功能描述：内容删除
     * 使用示范：
     *
     * @param id 主键ID
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/manage/content/{id}", method = RequestMethod.DELETE)
    public ResponseBean delCmsContent(@PathVariable("id") Long id) {
        return new ResponseBean(cmsContentService.deleteCmsContentById(id));
    }
}