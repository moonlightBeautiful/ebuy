package com.ims.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_bigType")
public class ProductBigType {
    /**
     * 唯一标识
     */
    private int id;
    /**
     * 名称
     */
    private String name;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 商品，1：n
     */
    private List<Product> productList = new ArrayList<Product>();
    /**
     * 商品小类，1：n
     */
    private List<ProductSmallType> smallTypeList = new ArrayList<ProductSmallType>();

    @Id
    @GeneratedValue(generator = "_native")
    @GenericGenerator(name = "_native", strategy = "native")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @OneToMany(mappedBy = "bigType")
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @OneToMany(mappedBy = "bigType", fetch = FetchType.EAGER)
    public List<ProductSmallType> getSmallTypeList() {
        return smallTypeList;
    }

    public void setSmallTypeList(List<ProductSmallType> smallTypeList) {
        this.smallTypeList = smallTypeList;
    }


}
