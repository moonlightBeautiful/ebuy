package com.ims.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ims.dao.BaseDAO;
import com.ims.entity.News;
import com.ims.entity.PageBean;
import com.ims.entity.Product;
import com.ims.service.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService{

	@Resource
	private BaseDAO<Product> baseDAO;
	
	@Override
	public List<Product> findProductList(Product s_product, PageBean pageBean) {
		List<Object> param=new LinkedList<Object>();
		StringBuffer hql=new StringBuffer("from Product");
		if(s_product!=null){
			if(s_product.getBigType()!=null){
				hql.append(" and bigType.id=?");
				param.add(s_product.getBigType().getId());
			}
			if(s_product.getSpecialPrice()==1){
				hql.append(" and specialPrice=1 order by specialPriceTime desc");
			} 
			if(s_product.getHot()==1){
				hql.append(" and hot=1 order by hotTime desc");
			}
		}
		if(pageBean!=null){
			return baseDAO.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
		}else{
			return null;
		}
	}

	@Override
	public Long getProductCount(Product s_product) {
		List<Object> param=new LinkedList<Object>();
		StringBuffer hql=new StringBuffer("select count(*) from Product");
		if(s_product!=null){
			if(s_product.getBigType()!=null){
				hql.append(" and bigType.id=?");
				param.add(s_product.getBigType().getId());
			}
		}
		return baseDAO.count(hql.toString().replaceFirst("and", "where"), param);
	}

}
