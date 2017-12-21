package com.bc.pmpheep.back.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bc.pmpheep.back.po.TopicWriter;

/**
 * 
 * 
 * 功能描述：选题申报编者信息持久层
 * 
 * 
 * 
 * @author (作者) 曾庆峰
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017年12月21日
 * @modify (最后修改时间)
 * @修改人 ：曾庆峰
 * @审核人 ：
 *
 */
@Repository
public interface TopicWriterDao {
	/**
	 * 
	 * 
	 * 功能描述：根据选题申报id获取编者情况
	 *
	 * @param topicId
	 * @return
	 *
	 */
	List<TopicWriter> listTopicWriterByTopicId(Long topicId);
}
