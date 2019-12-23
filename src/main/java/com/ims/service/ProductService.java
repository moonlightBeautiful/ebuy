package com.ims.service;

import java.util.List;

import com.ims.entity.Product;
import com.ims.entity.PageBean;


public interface ProductService {

    public List<Product> findHotProductList(Product s_product, PageBean pageBean);

    public List<Product> findSpecialProductList(Product s_product, PageBean pageBean);
}
