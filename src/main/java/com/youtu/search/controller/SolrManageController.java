package com.youtu.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youtu.common.pojo.YouTuResult;
import com.youtu.search.service.SolrManageService;

/**
 * 索引库维护
 *@author:王贤锐
 *@date:2018年1月21日  上午10:31:12
**/
@Controller
public class SolrManageController {
	@Autowired
	private SolrManageService solrManageService;
	
	@RequestMapping("/solrMange/importAllItem")
	@ResponseBody
	public YouTuResult importItems(){
		return solrManageService.importAllItems();
	}
}
