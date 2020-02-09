package com.siti.common.supply.po;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "supplier")
// 供应商表
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name; // 名称
    private String province; // 省
    private String city; // 市
    private Integer isFactory; // 是否工厂(0否;1是)
    private String wangId; // 旺旺号明文
    private Integer isGov; // 是否已被政府接管(0否;1是)
    private Integer needCustoms; // 是否需要清关(0否;1是)
    private Integer needAirshipping; // 是否需要空运(0否;1是)
    private BigDecimal gpsLong;
    private BigDecimal gpsLat;
    private Integer type; // 类型[0防护用具，1药品，2工具 等]
    private String notes;
    private Integer isActive; // 是否活跃(0否;1是)

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getIsFactory() {
        return isFactory;
    }

    public void setIsFactory(Integer isFactory) {
        this.isFactory = isFactory;
    }

    public String getWangId() {
        return wangId;
    }

    public void setWangId(String wangId) {
        this.wangId = wangId;
    }

    public Integer getIsGov() {
        return isGov;
    }

    public void setIsGov(Integer isGov) {
        this.isGov = isGov;
    }

    public Integer getNeedCustoms() {
        return needCustoms;
    }

    public void setNeedCustoms(Integer needCustoms) {
        this.needCustoms = needCustoms;
    }

    public Integer getNeedAirshipping() {
        return needAirshipping;
    }

    public void setNeedAirshipping(Integer needAirshipping) {
        this.needAirshipping = needAirshipping;
    }

    public BigDecimal getGpsLong() {
        return gpsLong;
    }

    public void setGpsLong(BigDecimal gpsLong) {
        this.gpsLong = gpsLong;
    }

    public BigDecimal getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(BigDecimal gpsLat) {
        this.gpsLat = gpsLat;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
}
