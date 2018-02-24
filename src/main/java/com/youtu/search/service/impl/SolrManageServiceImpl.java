package com.youtu.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youtu.common.pojo.YouTuResult;
import com.youtu.common.utils.ExceptionUtil;
import com.youtu.search.mapper.ItemMapper;
import com.youtu.search.pojo.Item;
import com.youtu.search.service.SolrManageService;

/**
 * 索引库维护业务
 *@author:王贤锐
 *@date:2018年1月21日  上午10:22:34
**/
@Service
public class SolrManageServiceImpl implements SolrManageService {
	@Autowired
	private ItemMapper itemMapper;
	
	//自动注入是以类的类型为匹配标准，这里会匹配到spring配置中的httpSolrServer类
	@Autowired
	private SolrServer solrServer;
	/**
	 * 导入商品信息到索引库中
	 */
	@Override
	public YouTuResult importAllItems() {
		try {
			// 取得商品信息
			List<Item> itemList = itemMapper.getItemList();
			//遍历list利用SolrInputDocument对象写入索引库中
			for (Item item : itemList) {
				SolrInputDocument document = new SolrInputDocument();
				document.setField("id",item.getId());
				document.setField("item_title", item.getTitle());
				document.setField("item_price", item.getPrice());
				document.setField("item_sell_point",item.getSell_point());
				document.setField("item_image",item.getImage());
				document.setField("item_category_name", item.getCategory_name());
				document.setField("item_desc", item.getItem_des());
				//写入索引库
				solrServer.add(document);
			}
			//提交生效
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
			//出错则返回报错信息
			return YouTuResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
		return YouTuResult.ok();
	}

}
