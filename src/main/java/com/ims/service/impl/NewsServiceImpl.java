package com.ims.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ims.dao.BaseDAO;
import com.ims.entity.News;
import com.ims.entity.Notice;
import com.ims.entity.PageBean;
import com.ims.service.NewsService;

@Service("newsService")
public class NewsServiceImpl implements NewsService{

	@Resource
	private BaseDAO<News> baseDAO;
	
	@Override
	public List<News> findNewsList(News s_news, PageBean pageBean) {
		List<Object> param=new LinkedList<Object>();
		StringBuffer hql=new StringBuffer("from News");
		if(pageBean!=null){
			return baseDAO.find(hql.toString(), param, pageBean);
		}else{
			return null;
		}
	}

}
