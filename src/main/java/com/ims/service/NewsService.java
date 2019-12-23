package com.ims.service;

import java.util.List;

import com.ims.entity.News;
import com.ims.entity.PageBean;

public interface NewsService {

	public List<News> findNewsList(News s_news, PageBean pageBean);
}
