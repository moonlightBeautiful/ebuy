package com.ims.service;

import java.util.List;

import com.ims.entity.PageBean;
import com.ims.entity.Product;

public interface ProductService {

    List<Product> findProductList(Product s_product, PageBean pageBean);

    Long getProductCount(Product s_product);
}
