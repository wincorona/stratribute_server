package com.siti.common.shipping.po;


public class ShippingAction {

  private long id;
  private long shippingNeedId;
  private String company;
  private String name;
  private String mobile;
  private long TrackingId;
  private long userId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getShippingNeedId() {
    return shippingNeedId;
  }

  public void setShippingNeedId(long shippingNeedId) {
    this.shippingNeedId = shippingNeedId;
  }


  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }


  public long getTrackingId() {
    return TrackingId;
  }

  public void setTrackingId(long TrackingId) {
    this.TrackingId = TrackingId;
  }


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

}
