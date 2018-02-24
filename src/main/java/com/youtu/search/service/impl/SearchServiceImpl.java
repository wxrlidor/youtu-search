package com.youtu.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtu.search.dao.SearchDao;
import com.youtu.search.pojo.SearchResult;
import com.youtu.search.service.SearchService;

/**
 * 查询索引库service
 *@author:王贤锐
 *@date:2018年1月21日  下午3:40:01
**/
@Service
public class SearchServiceImpl implements SearchService{
	@Autowired
	private SearchDao searchDao;
	
	/**
	 * 接收查询条件和分页page/rows,指定查询条件query、分页条件，默认搜索域、高亮显示。调用dao层返回查询结果，
	 * 并计算总页数和当前页码，最后返回searchResult对象
	 */
	@Override
	public SearchResult searchItem(String queryString, int page, int rows) throws Exception {
		// 创建查询对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery(queryString);
		//设置分页
		query.setStart((page -1) * rows);
		query.setRows(rows);
		//设置默认搜索域
		query.set("df","item_keywords");
		//设置高亮
		//1.打开高亮
		query.setHighlight(true);
		//2.设置高亮业务字段,商品标题item_title
		query.addHighlightField("item_title");
		//3.设置前后标签
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		
		//执行查询
		SearchResult result = searchDao.searchItem(query);
		//计算总页数=数量/每页条数
		long pageConut = result.getRecordCount() / rows;
		//如果有余数，就要添加一页
		if(result.getRecordCount() % rows >0 ){
			pageConut++;
		}
		result.setPageCount(pageConut);
		//设置当前页码
		result.setCurPage(page);
		
		return result;
	}

}
