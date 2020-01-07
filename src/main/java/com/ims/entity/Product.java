package com.ims.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_product")
public class Product {
    /**
     * 唯一标识
     */
    private int id;
    /**
     * 名称
     */
    private String name;
    /**
     * 价格
     */
    private int price;
    /**
     * 库存
     */
    private int stock;
    /**
     * 商品图片，这里是路径
     */
    private String proPic;
    /**
     * 描述
     */
    private String description;
    /**
     * 是否热卖 0非热卖 1热卖
     */
    private int hot;
    /**
     * 热卖时间
     */
    private Date hotTime;
    /**
     * 特价 0非特价 1特价
     */
    private int specialPrice;
    /**
     * 特价时间
     */
    private Date specialPriceTime;
    /**
     * 所属商品大类：商品vs商品大类=n：1
     */
    private ProductBigType bigType;
    /**
     * 所属商品小类：商品vs商品小类=n：1
     */
    private ProductSmallType smallType;
    /**
     * 商品和订单关系：n:n
     */
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

    @ManyToOne
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
