package com.ims.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_product")
public class Product {

    private int id;
    private String name;
    private int price;
    private int stock;
    private String proPic;
    private String description;
    /**
     * 是否热卖 0 不是热卖，1 热卖
     */
    private int hot;
    private Date hotTime;
    /**
     * 是否热卖 0 不是特价，1 特价
     */
    private int specialPrice;
    private Date specialPriceTime;

    private ProductBigType bigType;
    private ProductSmallType smallType;
    private List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProPic() {
        return proPic;
    }

    public void setProPic(String proPic) {
        this.proPic = proPic;
    }

    @Column(length = 2000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public Date getHotTime() {
        return hotTime;
    }

    public void setHotTime(Date hotTime) {
        this.hotTime = hotTime;
    }

    public int getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(int specialPrice) {
        this.specialPrice = specialPrice;
    }

    public Date getSpecialPriceTime() {
        return specialPriceTime;
    }

    public void setSpecialPriceTime(Date specialPriceTime) {
        this.specialPriceTime = specialPriceTime;
    }

    @ManyToOne(targetEntity = ProductBigType.class)
    @JoinColumn(name = "bigTypeId")
    public ProductBigType getBigType() {
        return bigType;
    }

    public void setBigType(ProductBigType bigType) {
        this.bigType = bigType;
    }

    @ManyToOne
    @JoinColumn(name = "smallTypeId")
    public ProductSmallType getSmallType() {
        return smallType;
    }

    public void setSmallType(ProductSmallType smallType) {
        this.smallType = smallType;
    }

    @OneToMany
    @JoinColumn(name = "productId")
    public List<OrderProduct> getOrderProductList() {
        return orderProductList;
    }

    public void setOrderProductList(List<OrderProduct> orderProductList) {
        this.orderProductList = orderProductList;
    }
}
