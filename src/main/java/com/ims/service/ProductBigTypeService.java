package com.ims.service;

import java.util.List;

import com.ims.entity.ProductBigType;

public interface ProductBigTypeService {
    /**
     * 获取所有商品大类
     * @return
     */
    public List<ProductBigType> findAllBigTypeList();
}
