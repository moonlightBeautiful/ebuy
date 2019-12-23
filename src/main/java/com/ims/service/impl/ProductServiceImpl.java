package com.ims.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import com.ims.service.ProductService;
import org.springframework.stereotype.Service;

import com.ims.dao.BaseDAO;
import com.ims.entity.News;
import com.ims.entity.PageBean;
import com.ims.entity.Product;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Resource
    private BaseDAO<Product> baseDAO;

    @Override
    public List<Product> findHotProductList(Product s_product, PageBean pageBean) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from Product where specialPrice=1 order by specialPriceTime desc");
        return baseDAO.find(hql.toString().replaceFirst("and", "where"), param, pageBean);

    }

    @Override
    public List<Product> findSpecialProductList(Product s_product, PageBean pageBean) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from Product where hot=1 order by hotTime desc");
        return baseDAO.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
    }

}
