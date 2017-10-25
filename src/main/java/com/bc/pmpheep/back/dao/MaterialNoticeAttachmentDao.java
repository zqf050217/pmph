/**
 * 
 */
package com.bc.pmpheep.back.dao;


import org.springframework.stereotype.Repository;
import com.bc.pmpheep.back.po.MaterialNoticeAttachment;


/**
 * 教材通知附件表
 * @introduction
 *
 * @author Mryang
 *
 * @createDate 2017年10月25日 下午3:33:18
 *
 */
@Repository
public interface MaterialNoticeAttachmentDao {
	
	/**
	 * 新增 materialNoticeAttachment
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年10月25日 下午3:34:22
	 * @param materialNoticeAttachment
	 * @return 影响行数
	 */
	Integer addMaterialNoticeAttachment(MaterialNoticeAttachment materialNoticeAttachment);
	
	/**
	 * 更新 materialNoticeAttachment
	 * @introduction 
	 * @author Mryang
	 * @createDate 2017年10月25日 下午3:44:07
	 * @param materialNoticeAttachment
	 * @return 影响行数
	 */
	Integer updateMaterialNoticeAttachment(MaterialNoticeAttachment materialNoticeAttachment);
}
