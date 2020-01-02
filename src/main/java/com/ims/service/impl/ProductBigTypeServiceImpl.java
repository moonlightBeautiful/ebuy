package com.ims.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ims.dao.BaseDAO;
import com.ims.entity.ProductBigType;
import com.ims.service.ProductBigTypeService;

@Service("productBigTypeService")
public class ProductBigTypeServiceImpl implements ProductBigTypeService{

	@Resource
	private BaseDAO<ProductBigType> baseDAO;
	
	@Override
	public List<ProductBigType> findAllBigTypeList() {
		return baseDAO.find(" from ProductBigType");
	}

}
