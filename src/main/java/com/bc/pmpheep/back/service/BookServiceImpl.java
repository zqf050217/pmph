package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.BookDao;
import com.bc.pmpheep.back.dao.BookDetailDao;
import com.bc.pmpheep.back.dao.BookUserCommentDao;
import com.bc.pmpheep.back.dao.BookUserLikeDao;
import com.bc.pmpheep.back.dao.BookUserMarkDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Book;
import com.bc.pmpheep.back.po.BookDetail;
import com.bc.pmpheep.back.po.BookUserLike;
import com.bc.pmpheep.back.po.BookUserMark;
import com.bc.pmpheep.back.util.ArrayUtil;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ContactMallUtil;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.MD5;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.BookPreferenceAnalysisVO;
import com.bc.pmpheep.back.vo.BookVO;
import com.bc.pmpheep.erp.service.InfoWorking;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

@Service
public class BookServiceImpl extends BaseService implements BookService {
	private static Properties pmphapiconfigPro = null;
	private static InputStream pmphapiconfigIs = null;

	@Autowired
	BookDao bookDao;
	@Autowired
	BookDetailDao bookDetailDao;
	@Autowired
	BookUserCommentDao bookUserCommentDao;
	@Autowired
	BookUserMarkDao bookUserMarkDao;
	@Autowired
	BookUserLikeDao bookUserLikeDao;

	@Override
	public PageResult<BookVO> listBookVO(PageParameter<BookVO> pageParameter) throws CheckedServiceException {
		if (ObjectUtil.isNull(pageParameter.getParameter())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		String path = pageParameter.getParameter().getPath();
		Long type = pageParameter.getParameter().getType();
		if (StringUtil.notEmpty(path) && ObjectUtil.notNull(type)) {
			pageParameter.getParameter().setPath(path + "-" + String.valueOf(type));
		}
		int total = bookDao.getBookVOTotal(pageParameter);
		PageResult<BookVO> pageResult = new PageResult<>();
		if (total > 0) {
			PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
			pageResult.setRows(bookDao.listBookVO(pageParameter));
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	public String updateBookById(Long[] ids, Long type, Boolean isOnSale, Boolean isNew, Boolean isPromote,
			Long materialId) throws CheckedServiceException {
		if (ArrayUtil.isEmpty(ids)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "id为空");
		}
		if (ObjectUtil.isNull(type)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"书籍类型为空");
		}
		if (ObjectUtil.isNull(isOnSale)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"是否上架为空");
		}
		if (ObjectUtil.isNull(isNew)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"是否新书为空");
		}
		if (ObjectUtil.isNull(isPromote)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"是否推荐为空");
		}
		String result = "FAIL";
		for (Long id : ids) {
			Book book = new Book();
			book.setId(id);
			book.setType(type);
			book.setIsNew(isNew);
			book.setIsOnSale(isOnSale);
			book.setIsPromote(isPromote);
			book.setMaterialId(materialId);
			bookDao.updateBook(book);
		}
		result = "SUCCESS";
		return result;
	}

	@Override
	public Book add(Book book) {
		if (ObjectUtil.isNull(book)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"图书对象为空");
		}
		bookDao.addBook(book);
		return book;
	}

	@Override
	public BookDetail add(BookDetail detail) {
		if (ObjectUtil.isNull(detail)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"图书详情对象为空");
		}
		bookDetailDao.addBookDetail(detail);
		return detail;
	}

	@Override
	public BookUserLike add(BookUserLike like) {
		if (ObjectUtil.isNull(like)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"图书点赞对象为空");
		}
		bookUserLikeDao.addBookUserLike(like);
		return like;
	}

	@Override
	public BookUserMark add(BookUserMark mark) {
		if (ObjectUtil.isNull(mark)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"图书收藏对象为空");
		}
		bookUserMarkDao.addBookUserMark(mark);
		return mark;
	}

	@Override
	public String AbuttingJoint(String[] vns, Integer type) throws CheckedServiceException {
		String result = "SUCCESS";
		for (int i = 0; i < vns.length; i++) {
			JSONObject ot = new JSONObject();
			if (type == 0) {// 商城发送修改的请求
				if (ObjectUtil.isNull(bookDao.getBookByVn(vns[i]))) {
					continue;
				}
			}
			try {
//				System.out.println(vns[i] + " " + "第" + i + "号本版号");
				ot = PostBusyAPI(vns[i]);
				if ("1".equals(ot.getJSONObject("RESP").getString("CODE"))) {
					// 请求成功并正常返回
					if (type == 1) {
						emptyBooks(vns[i]);// 如果请求成功有返回值时，清除所有数据
					}
					JSONArray array = ot.getJSONObject("RESP").getJSONObject("responseData").getJSONArray("results");
					if (array.size() > 0) {
						Book book = BusyResJSONToModel(array.getJSONObject(0), null);
						String content = book.getContent();// 获取到图书详情将其存入到图书详情表中
						if (ObjectUtil.isNull(book.getId())) {
							book.setScore(9.0);
							bookDao.addBook(book);
							BookDetail bookDetail = new BookDetail(book.getId(), content);
							bookDetailDao.addBookDetail(bookDetail);
						} else {
							bookDao.updateBook(book);
							BookDetail bookDetail = new BookDetail(book.getId(), content);
							bookDetailDao.updateBookDetailByBookId(bookDetail);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = "FAIL";
			}
		}
		return result;
	}

	/**
	 * 
	 * 
	 * 功能描述: 获取数据
	 *
	 * @param editNumbers
	 * @param config
	 * @return
	 * @throws IOException
	 *
	 */
	private JSONObject PostBusyAPI(String vn, String... config) throws IOException {
		String uri = "", app_key = "", app_secret = "", session_key = "";
		if (config != null) {
			if (pmphapiconfigPro == null || pmphapiconfigIs == null) {
				pmphapiconfigPro = new Properties();
				pmphapiconfigIs = BookServiceImpl.class.getClassLoader()
						.getResourceAsStream("pmphapi-config.properties");
				pmphapiconfigPro.load(pmphapiconfigIs);
			}
			uri = pmphapiconfigPro.getProperty("uri");
			app_key = pmphapiconfigPro.getProperty("app_key");
			app_secret = pmphapiconfigPro.getProperty("app_secret");
			session_key = pmphapiconfigPro.getProperty("session_key");
		} else {
			uri = config[0];
			app_key = config[1];
			app_secret = config[2];
			session_key = config[3];
		}
		String sendTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
		StringBuffer sbSgin = new StringBuffer();
		sbSgin.append(app_secret);
		sbSgin.append("app_key" + app_key + "&");
		sbSgin.append("formatjson&");
		sbSgin.append("methodcom.ai.ecp.pmph.gdsdetail.services&");
		sbSgin.append("session" + session_key + "&");
		sbSgin.append("sign_methodmd5&");
		sbSgin.append("timestamp" + sendTime + "&");
		sbSgin.append("v1.0&");
		sbSgin.append("versionNumber" + vn);
		sbSgin.append(app_secret);
		String strSign = MD5.md5(sbSgin.toString()).toLowerCase();

		StringBuffer sbPar = new StringBuffer();
		sbPar.append("sign=" + strSign + "&");
		sbPar.append("timestamp=" + URLEncoder.encode(sendTime, "UTF-8") + "&");
		sbPar.append("sign_method=md5&");
		sbPar.append("session=" + session_key + "&");
		sbPar.append("method=com.ai.ecp.pmph.gdsdetail.services&");
		sbPar.append("format=json&");
		sbPar.append("app_key=" + app_key + "&");
		sbPar.append("v=1.0&");
		sbPar.append("versionNumber=" + URLEncoder.encode(vn, "UTF-8"));
		String strRes = ContactMallUtil.getInfomation(uri, sbPar.toString());
		return JSONObject.fromObject(strRes);
	}

	/**
	 * 
	 * 
	 * 功能描述：将获取的信息装到Book里面去
	 *
	 * @param item
	 * @param model
	 * @return
	 *
	 */
	private Book BusyResJSONToModel(JSONObject item, Book model) {
		if (model == null) {
			model = bookDao.getBookByVn(item.getString("versionNumber"));
			if (model == null) {
				model = new Book();
				model.setIsPromote(true);
				model.setIsOnSale(true);
				model.setIsNew(true);
				model.setSales(0L);
				model.setGmtCreate(DateUtil.getCurrentTime());
			}
			model = model == null ? new Book() : model;
		}
		String revison = item.getString("edition");
		if (null == revison || "".equals(revison)) {
			revison = "0";
		}
		String isbn = item.getString("isbn");
		if (StringUtil.isEmpty(isbn) || "".equals(isbn)) {
			isbn = "-";
		}
		if (CollectionUtil.isEmpty(item.getJSONArray("gdsDescList"))) {// 内容简介
			model.setContent("暂缺"); // 内容简介
		} else {
			model.setContent(item.getJSONArray("gdsDescList").getJSONObject(0).getString("gdsDescContent"));
		}
		model.setRevision(Integer.parseInt(revison)); // 版次 ,印次
		model.setBookname(item.getString("gdsName")); // 书名
		model.setAuthor(item.getString("author")); // 作者
		model.setReader(item.getString("reader")); // 读者对象
		model.setPublishDate(DateUtil.str3Date(item.getString("publicDate"))); // 出版时间
		model.setPublisher(item.getString("publicCompany")); // 出版社
		model.setLang(item.getString("language")); // 语言
		model.setImageUrl(item.getJSONArray("imageList").size() > 0
				? item.getJSONArray("imageList").getJSONObject(0).getString("imgUrl")
				: ""); // 图片地址
		model.setPdfUrl(item.getString("pdfFile"));
		model.setBuyUrl(item.getString("webGdsDetailUrl"));
		model.setVn(item.getString("versionNumber"));
		model.setIsbn(isbn);
		return model;
	}

	/**
	 * 
	 * 
	 * 功能描述：清除书籍与其相关的所有的数据
	 *
	 * @param vn
	 *            书籍本版号
	 * @return
	 *
	 */
	private void emptyBooks(String vn) {
		// 1.根据本版号搜索出书籍id 2.根据书籍id删除关联表的所有数据
		Book book = bookDao.getBookByVn(vn);
		if (!ObjectUtil.isNull(book)) {
			Long id = book.getId();
			bookDao.deleteBookById(id);
			bookDetailDao.deleteBookDetailByBookId(id);
			bookUserCommentDao.deleteBookUserCommentByBookId(id);
			bookUserLikeDao.deleteBookUserLikeByBookId(id);
			bookUserMarkDao.deleteBookUserMarkByBookId(id);
		}
	}

	@Override
	public String deleteBookById(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"书籍id为空");
		}
		bookDao.deleteBookById(id);
		bookDetailDao.deleteBookDetailByBookId(id);
		bookUserCommentDao.deleteBookUserCommentByBookId(id);
		bookUserLikeDao.deleteBookUserLikeByBookId(id);
		bookUserMarkDao.deleteBookUserMarkByBookId(id);
		return "SUCCESS";
	}

	@Override
	public String AllSynchronization(Integer type) throws CheckedServiceException {
		if (ObjectUtil.isNull(type)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"同步中产生了错误，请重新同步");
		}
		String[] vns = new InfoWorking().listBookInfo();
		vns = ArrayUtil.array_unique(vns);
		if (1 == type) {
			AbuttingJoint(vns, type);
		} else {
			List<String> list = new ArrayList<>();
			for (String vn : vns) {
				Book book = bookDao.getBookByVn(vn);
				if (ObjectUtil.isNull(book)) {
					list.add(vn);
				}
			}
			String editionnums[] = new String[list.size()];
			for (int i = 0, j = list.size(); i < j; i++) {
				editionnums[i] = list.get(i);
			}
			AbuttingJoint(editionnums, type);
		}

		return "SUCCESS";
	}

	@Override
	public PageResult<BookPreferenceAnalysisVO> getBookPreferenceAnalysis(
			PageParameter<BookPreferenceAnalysisVO> pageParameter) throws CheckedServiceException {
		if (ObjectUtil.isNull(pageParameter.getParameter())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM,
					"查询出现问题，请重新加载");
		}
		String path = pageParameter.getParameter().getPath();
		Long type = pageParameter.getParameter().getType();
		if (StringUtil.notEmpty(path) && ObjectUtil.notNull(type)) {
			path = path + "-" + String.valueOf(type);
		}
		PageResult<BookPreferenceAnalysisVO> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		Integer total = bookDao.getBookPreferenceAnalysisTotal(pageParameter.getParameter().getBookname(),
				pageParameter.getParameter().getType(), pageParameter.getParameter().getPath());
		if (total > 0) {
			pageResult.setRows(bookDao.getBookPreferenceAnalysis(pageParameter.getParameter().getBookname(),
					pageParameter.getStart(), pageParameter.getPageSize(), pageParameter.getParameter().getType(),
					pageParameter.getParameter().getPath()));
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	public PageResult<Book> listBook(Integer pageSize, Integer pageNumber, String bookName)
			throws CheckedServiceException {
		Map<String, Object> map = new HashMap<String, Object>(3);
		if (!StringUtil.isEmpty(bookName)) {
			bookName = StringUtil.toAllCheck(bookName.trim());
			map.put("bookName", bookName);
		}
		map.put("start", ((pageNumber - 1) * pageSize));
		map.put("pageSize", pageSize);
		PageResult<Book> pageResult = new PageResult<Book>();
		pageResult.setPageNumber(pageNumber);
		pageResult.setPageSize(pageSize);
		List<Book> rows = new ArrayList<Book>(1);
		Integer total = bookDao.getListToatl(map);
		if (null != total && total.intValue() > 0) {
			rows = bookDao.geList(map);
		}
		pageResult.setTotal(total);
		pageResult.setRows(rows);
		return pageResult;
	}

	@Override
	public Integer updateBookCore(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return bookDao.updateBookCore(id);
	}

	@Override
	public void updateUpComments(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		bookDao.updateUpComments(id);
	}

	@Override
	public void updateDownComments(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.BOOK, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		bookDao.updateDownComments(id);
	}

}
