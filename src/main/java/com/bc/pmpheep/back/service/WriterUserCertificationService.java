package com.bc.pmpheep.back.service;


import com.bc.pmpheep.back.po.WriterUserCertification;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * @introduction  WriterUserCertificationService 接口
 *
 * @author Mryang
 *
 * @createDate 2017年9月19日 上午9:54:29
 *
 */
public interface WriterUserCertificationService {
	
	/**
	 * 
	 * @introduction 新增一个WriterUserCertification
	 * @author Mryang
	 * @createDate 2017年9月19日 上午9:50:09
	 * @param writerUserCertification
	 * @return  带主键的WriterUserCertification
	 * @throws CheckedServiceException
	 */
	WriterUserCertification addWriterUserCertification(WriterUserCertification writerUserCertification) throws CheckedServiceException;

	/**
	 * 
	 * @introduction  根据id查询 WriterUserCertification 
	 * @author Mryang
	 * @createDate 2017年9月19日 上午9:51:41
	 * @param id
	 * @return  WriterUserCertification
	 * @throws CheckedServiceException
	 */
	WriterUserCertification getWriterUserCertificationById(Long id) throws CheckedServiceException;

	/**
	 * 
	 * @introduction 根据id删除WriterUserCertification 
	 * @author Mryang
	 * @createDate 2017年9月19日 上午9:52:18
	 * @param id
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer deleteWriterUserCertificationById(Long id) throws CheckedServiceException;

	/**
	 * 
	 * @introduction  根据id 更新writerUserCertification不为null和''的字段
	 * @author Mryang
	 * @createDate 2017年9月19日 上午9:53:00
	 * @param writerUserCertification
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	Integer updateWriterUserCertification(WriterUserCertification writerUserCertification) throws CheckedServiceException ;
}
