package com.siti.common.supply.po;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "supplier_products")
// 供应商产品库存表
// （需方产品表中为全系统唯一确定，供应商产品库存列表每个供应商每种商品存在一条记录）
public class SupplierProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name; // 名称
    private Integer type; // 类型[0防护用具，1药品，2工具 等]
    private String model; // 型号[str,例如n95，9051等]
    private String upc; // 产品条码
    private Integer ifMust; // 是否必须无替代品
    private Integer ifSpecial; // 是否特需[例如ecmo仪器等为特需，口罩等为不特需]
    private BigDecimal unitPrice; // 厂方报价
    private Integer supplierId; // 供应商表id
    private Integer productId; // 需方产品表id
    private Integer userId;
    private Date timestampPost; // yyyy-MM-dd hh:mm:ss
    private Date timestampLastEdit; // yyyy-MM-dd hh:mm:ss

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public Integer getIfMust() {
        return ifMust;
    }

    public void setIfMust(Integer ifMust) {
        this.ifMust = ifMust;
    }

    public Integer getIfSpecial() {
        return ifSpecial;
    }

    public void setIfSpecial(Integer ifSpecial) {
        this.ifSpecial = ifSpecial;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getTimestampPost() {
        return timestampPost;
    }

    public void setTimestampPost(Date timestampPost) {
        this.timestampPost = timestampPost;
    }

    public Date getTimestampLastEdit() {
        return timestampLastEdit;
    }

    public void setTimestampLastEdit(Date timestampLastEdit) {
        this.timestampLastEdit = timestampLastEdit;
    }
}
