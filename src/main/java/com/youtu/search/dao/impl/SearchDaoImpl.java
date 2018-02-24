package com.youtu.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.youtu.search.dao.SearchDao;
import com.youtu.search.pojo.Item;
import com.youtu.search.pojo.SearchResult;

/**
 *@author:王贤锐
 *@date:2018年1月21日  下午2:38:02
**/
@Repository
public class SearchDaoImpl implements SearchDao {
	@Autowired
	private SolrServer solrServer;
	/**
	 * 根据查询条件搜索商品信息
	 */
	@Override
	public SearchResult searchItem(SolrQuery query) throws Exception{
		//返回结果对象
		SearchResult result = new SearchResult();
		//商品列表
		List<Item> itemList = new ArrayList<>();
		//查询索引库
		QueryResponse queryResponse = solrServer.query(query);
		//取出查询结果
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		//取出查询结果总数
		result.setRecordCount(solrDocumentList.getNumFound());
		//取出item_title高亮结果，高亮对象第一层是id，第二层是你需要的业务字段名
		//取出后是一个list
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		//遍历查询结果的list，取出商品信息，加入到返回结果中
		for (SolrDocument solrDocument : solrDocumentList) {
			Item item = new Item();
			item.setId((String) solrDocument.get("id"));
			//根据商品id取出item_title的高亮结果
			List<String> hightList = highlighting.get(solrDocument.get("id")).get("item_title");
			String title ="";
			//判断是否有高亮结果，因为匹配的域是item_keywords，所以匹配到的有可能是描述或者卖点
			if(hightList != null && hightList.size()>0){
				title = hightList.get(0);
			}else{
				//取不到，则使用非高亮的title
				title = (String) solrDocument.get("item_title");
			}
			item.setTitle(title);
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			
			itemList.add(item);
		}
		result.setItemList(itemList);
		
		return result;
	}

}
