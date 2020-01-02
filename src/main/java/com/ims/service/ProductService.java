package com.ims.service;

import java.util.List;

import com.ims.entity.PageBean;
import com.ims.entity.Product;

public interface ProductService {

	public List<Product> findProductList(Product s_product, PageBean pageBean);
	
	public Long getProductCount(Product s_product);
}
