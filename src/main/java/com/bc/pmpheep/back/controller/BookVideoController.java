package com.bc.pmpheep.back.controller;

import java.util.Arrays;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.BookVideo;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.vo.PastBookVideoVO;
import com.bc.pmpheep.back.vo.BookVideoVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.back.service.BookVideoService;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author MrYang
 * @CreateDate 2018年2月6日 下午5:19:00
 *
 *
 */
@Controller
@RequestMapping("/bookVideo")
public class BookVideoController {

    // 当前业务类型
    private static final String BUSSINESS_TYPE = "微视频";

    @Autowired
    private BookVideoService bookVideoService;

    /**
     * 查询书籍视频
     *
     * @introduction
     * @author Mryang
     * @createDate 2018年2月6日 下午5:34:12
     * @param pageSize
     * @param pageNumber
     * @param bookName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getVideoList", method = RequestMethod.GET)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询微视频列表")
    public ResponseBean<PageResult<BookVideoVO>> getVideoList(
            Integer pageSize,
            Integer pageNumber,
            String bookName,
            Integer state,
            String upLoadTimeStart,
            String upLoadTimeEnd) {
        pageSize = (pageSize == null || pageSize <= 0) ? 5 : pageSize;
        pageNumber = (pageNumber == null || pageNumber <= 0) ? 1 : pageNumber;
        return new ResponseBean<PageResult<BookVideoVO>>(
                bookVideoService.getVideoList(pageSize, pageNumber, bookName, state, upLoadTimeStart, upLoadTimeEnd));
    }

    /**
     * 查询书籍视频
     *
     * @introduction
     * @author Mryang
     * @createDate 2018年2月6日 下午5:34:12
     * @param pageSize
     * @param pageNumber
     * @param bookName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询微视频列表")
    public ResponseBean<PageResult<PastBookVideoVO>> getList(Integer pageSize, Integer pageNumber, String bookName) {
        pageSize = (pageSize == null || pageSize <= 0) ? 5 : pageSize;
        pageNumber = (pageNumber == null || pageNumber <= 0) ? 1 : pageNumber;
        return new ResponseBean<PageResult<PastBookVideoVO>>(bookVideoService.getList(pageSize, pageNumber, bookName));
    }

    /**
     * 审核视频
     *
     * @introduction
     * @author Mryang
     * @createDate 2018年2月6日 下午5:34:12
     * @param pageSize
     * @param pageNumber
     * @param bookName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/audit", method = RequestMethod.PUT)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "微视频审核")
    public ResponseBean<Integer> audit(HttpServletRequest request, Long id, Boolean isAuth) {
        BookVideo bookVideo = new BookVideo();
        bookVideo
                .setId(id)
                .setAuthDate(new Date())
                .setAuthUserId(SessionUtil.getPmphUserBySessionId(CookiesUtil.getSessionId(request)).getId())
                .setIsAuth(isAuth == null ? false : isAuth);
        return new ResponseBean(bookVideoService.updateBookVideo(bookVideo));
    }

    /**
     * 删除视频
     *
     * @introduction
     * @author Mryang
     * @createDate 2018年2月6日 下午5:34:12
     * @param pageSize
     * @param pageNumber
     * @param bookName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteBookVideo", method = RequestMethod.PUT)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除微视频")
    public ResponseBean<Integer> deleteBookVideo(Long id) {
        return new ResponseBean(bookVideoService.deleteBookVideoByIds(Arrays.asList(new Long[]{id})));
    }

    /**
     * 保存视频
     *
     * @param request
     * @param cover
     * @throws java.io.IOException
     * @introduction
     * @author Mryang
     * @createDate 2018年2月10日 下午5:34:12
     * @param bookVideo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addBookVideo", method = RequestMethod.POST)
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "保存微视频信息")
    public ResponseBean<Integer> addBookVideo(HttpServletRequest request, BookVideo bookVideo,
            @RequestParam("cover") MultipartFile cover) throws IOException {
        String sessionId = CookiesUtil.getSessionId(request);
        if (StringUtil.isEmpty(sessionId)) {
            return new ResponseBean(new CheckedServiceException(CheckedExceptionBusiness.BOOK_VEDIO,
                    CheckedExceptionResult.USER_SESSION, "尚未登录或session已过期"));
        }
        return new ResponseBean(bookVideoService.addBookVideo(sessionId, bookVideo, cover));
    }

}
