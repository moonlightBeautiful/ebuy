package com.ims.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ims.dao.BaseDAO;
import com.ims.entity.Notice;
import com.ims.entity.PageBean;
import com.ims.service.NoticeService;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService{

	@Resource
	private BaseDAO<Notice> baseDAO;
	
	@Override
	public List<Notice> findNoticeList(Notice s_notice, PageBean pageBean) {
		List<Object> param=new LinkedList<Object>();
		StringBuffer hql=new StringBuffer("from Notice");
		if(pageBean!=null){
			return baseDAO.find(hql.toString(), param, pageBean);
		}else{
			return null;
		}
	}

}
