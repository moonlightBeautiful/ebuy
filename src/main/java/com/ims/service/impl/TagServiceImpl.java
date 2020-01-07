package com.ims.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ims.dao.BaseDAO;
import com.ims.entity.ProductBigType;
import com.ims.entity.Tag;
import com.ims.service.TagService;

@Service("tagService")
public class TagServiceImpl implements TagService {

    @Resource
    private BaseDAO<Tag> baseDAO;

    @Override
    public List<Tag> findTagList() {
        StringBuffer hql = new StringBuffer("from Tag");
        return baseDAO.find(hql.toString());
    }

}
