package com.bc.pmpheep.back.dao;

import com.bc.pmpheep.back.po.BookUserLike;
import org.springframework.stereotype.Repository;

@Repository
public interface BookUserLikeDao {

	/**
	 * 新增一个Book
	 * 
	 * @param BookUserLike
	 *            实体对象
	 * @return 影响行数
	 */
	Integer addBookUserLike(BookUserLike bookUserLike);

	/**
	 * 删除Book 通过主键id
	 * 
	 * @param BookUserLike
	 * @return 影响行数
	 */
	Integer deleteBookUserLikeById(Long id);

	/**
	 * 更新一个 Book通过主键id
	 * 
	 * @param BookUserLike
	 * @return 影响行数
	 */
	Integer updateBookUserLike(BookUserLike bookUserLike);

	/**
	 * 查询一个 Book 通过主键id
	 * 
	 * @param BookUserLike
	 *            必须包含主键ID
	 * @return BookUserLike
	 */
	BookUserLike getBookUserLikeById(Long id);
}
