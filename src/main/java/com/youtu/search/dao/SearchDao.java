package com.youtu.search.dao;
/**
 *@author:王贤锐
 *@date:2018年1月21日  下午2:37:05
**/

import org.apache.solr.client.solrj.SolrQuery;

import com.youtu.search.pojo.SearchResult;

public interface SearchDao {
	SearchResult searchItem(SolrQuery query) throws Exception;
}
