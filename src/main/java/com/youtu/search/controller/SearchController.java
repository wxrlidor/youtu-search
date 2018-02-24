package com.youtu.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youtu.common.pojo.YouTuResult;
import com.youtu.common.utils.ExceptionUtil;
import com.youtu.search.pojo.SearchResult;
import com.youtu.search.service.SearchService;

/**
 * 搜索查询Controller
 * 
 * @author:王贤锐
 * @date:2018年1月21日 下午4:12:13
 **/
@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;

	/**
	 * 接收查询参数：查询条件、page、rows 调用Service执行查询返回一个查询结果对象。
	 * 把查询结果包装到YoutuResult中返回，结果是json格式的数据。 如果查询条件为空，返回状态码：400，消息：查询条件不能为空。
	 * Page为空：默认为1 Rows 为空：默认为60
	 * 
	 * @return
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public YouTuResult searchItem(@RequestParam(value = "q") String queryString,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "60") Integer rows) {
		//判断查询条件是否为空
		if (StringUtils.isBlank(queryString)) {
			return YouTuResult.build(400, "查询条件q不能为空");
		}
		SearchResult result = null;

		try {
			//解决中文乱码问题
			queryString =new String(queryString.getBytes("iso8859-1"),"utf-8");
			result = searchService.searchItem(queryString, page, rows);
		} catch (Exception e) {
			e.printStackTrace();
			// 查询出错就返回异常信息
			return YouTuResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		return YouTuResult.ok(result);
	}
}
