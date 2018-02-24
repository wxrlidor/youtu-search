package com.youtu.search.test;
/**
 *@author:王贤锐
 *@date:2018年1月20日  下午4:02:15
**/

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {
	/**
	 * 写入solr
	 * @throws SolrServerException
	 * @throws IOException
	 */
	@Test
	public void addDoucment() throws SolrServerException, IOException{
		//创建一个solr服务对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.168.121:8080/solr");
		//创建文档对象并加入field属性
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "Test001");
		document.addField("item_title", "商品2号");
		//写入索引库中并提交
		solrServer.add(document);
		solrServer.commit();
	}
	/**
	 * 删除solr
	 * @throws Exception
	 */
	@Test
	public void deleteDocument() throws Exception {
		//创建一连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.168.121:8080/solr");
		//根据id删除
		solrServer.deleteById("Test001");
		//删除所有
		//solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}
	/**
	 * solr查询
	 * @throws SolrServerException 
	 */
	@Test
	public void queryDocument() throws SolrServerException{
		//创建一个solr服务对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.168.121:8080/solr");
		//创建query对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery("*:*");
		//从第11条开始取，索引库数据的下标是从0开始的
		query.setStart(10);
		//总共取20条记录，也就是第11~第31条记录被取出来了
		query.setRows(20);
		//执行查询
		QueryResponse response = solrServer.query(query);
		//取查询结果，documentList是继承ArrayList的
		SolrDocumentList documentList = response.getResults();
		System.out.println("索引库中总共有的记录数："+documentList.getNumFound());
		//遍历list取出结果
		for (SolrDocument solrDocument : documentList) {
			System.out.println("id: "+ solrDocument.get("id"));
			System.out.println("title: "+ solrDocument.get("item_title"));
		}
		
	}
}
