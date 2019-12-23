package com.ims.service;

import java.util.List;

import com.ims.entity.Notice;
import com.ims.entity.PageBean;

public interface NoticeService {

	public List<Notice> findNoticeList(Notice s_notice, PageBean pageBean);
}
