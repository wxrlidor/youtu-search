package com.youtu.search.service;
/**
 *@author:王贤锐
 *@date:2018年1月21日  下午3:38:55
**/

import com.youtu.search.pojo.SearchResult;

public interface SearchService {
	SearchResult searchItem(String queryString, int page, int rows) throws Exception;
}
