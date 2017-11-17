package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.dao.BookUserCommentDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.service.BookUserCommentService;
import com.bc.pmpheep.back.vo.BookUserCommentVO;
import com.bc.pmpheep.test.BaseTest;

/**
 * 图书评论业务层单元测试
 * 
 * @author
 * @date 2017-11-06
 * @修改人 mr
 */
public class BookUserCommentServiceTest extends BaseTest {
    Logger                 logger = LoggerFactory.getLogger(BookUserCommentServiceTest.class);

    @Resource
    BookUserCommentService bookUserCommentService;
    @Resource
    BookUserCommentDao     bookUserCommentDao;

    @Test
    public void testListBookUserComment() {
        PageParameter<BookUserCommentVO> pageParameter = new PageParameter<>(1, 1);
        BookUserCommentVO bookUserCommentVO = new BookUserCommentVO();
        bookUserCommentVO.setIsAuth(true);
        bookUserCommentVO.setName("名字");
        pageParameter.setParameter(bookUserCommentVO);
        PageResult<BookUserCommentVO> pageResult =
        bookUserCommentService.listBookUserComment(pageParameter);
        Assert.assertTrue("分页初始化/查询图书评论", pageResult.getRows().isEmpty());
    }

    @Test
    public void testDeleteBookUserCommentById() {
        Long[] ids = { 1L, 2L };
        String result2 = bookUserCommentService.deleteBookUserCommentById(ids);
        Assert.assertTrue("删除是否成功", "SUCCESS".equals(result2));
    }

}
