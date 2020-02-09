package com.siti.common.shipping.vo;

/**
 * Created by 12293 on 2020/2/9.
 */
public class ScopeInfo {

    Double startGpsLong;
    Double startGpsLat;
    Double kmLimit;
    String provinceLimit;

    public Double getStartGpsLong() {
        return startGpsLong;
    }

    public ScopeInfo setStartGpsLong(Double startGpsLong) {
        this.startGpsLong = startGpsLong;
        return this;
    }

    public Double getStartGpsLat() {
        return startGpsLat;
    }

    public ScopeInfo setStartGpsLat(Double startGpsLat) {
        this.startGpsLat = startGpsLat;
        return this;
    }

    public Double getKmLimit() {
        return kmLimit;
    }

    public ScopeInfo setKmLimit(Double kmLimit) {
        this.kmLimit = kmLimit;
        return this;
    }

    public String getProvinceLimit() {
        return provinceLimit;
    }

    public ScopeInfo setProvinceLimit(String provinceLimit) {
        this.provinceLimit = provinceLimit;
        return this;
    }
}
