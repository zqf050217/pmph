package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.BookDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDetailDao {
/**
	 * ����һ��BookDetail
	 * 
	 * @param BookDetail
	 *            ʵ�����
	 * @return Ӱ������
	 */
	Integer addBookDetail(BookDetail bookDetail);

	/**
	 * ɾ��BookDetail ͨ������id
	 * 
	 * @param BookDetail
	 * @return Ӱ������
	 */
	Integer deleteBookDetailById(Long id);

	/**
	 * ����һ�� BookDetailͨ������id
	 * 
	 * @param BookDetail
	 * @return Ӱ������
	 */
	Integer updateBookDetail(BookDetail bookDetail);

	/**
	 * ��ѯһ�� BookDetail ͨ������id
	 * 
	 * @param BookDetail
	 *            �����������ID
	 * @return BookDetail
	 */
	BookDetail getBookDetailById(Long id);
}