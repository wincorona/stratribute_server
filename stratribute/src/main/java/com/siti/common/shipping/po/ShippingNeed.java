package com.siti.common.shipping.po;

import javax.persistence.Id;

public class ShippingNeed {

  @Id
  private String id;
  private long matchId;
  private String senderAddress;
  private String receiverAddress;
  private String senderGpsLong;
  private String senderGpsLat;
  private String receiverGpsLong;
  private String receiverGpsLat;
  private String description;
  private long isPellet;
  private long isDelicate;
  private long needChilled;
  private double boxSizeX;
  private double boxSizeY;
  private double boxSizeZ;
  private double boxWeight;
  private long numberOfBoxes;
  private double budget;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public long getMatchId() {
    return matchId;
  }

  public void setMatchId(long matchId) {
    this.matchId = matchId;
  }


  public String getSenderAddress() {
    return senderAddress;
  }

  public void setSenderAddress(String senderAddress) {
    this.senderAddress = senderAddress;
  }


  public String getReceiverAddress() {
    return receiverAddress;
  }

  public void setReceiverAddress(String receiverAddress) {
    this.receiverAddress = receiverAddress;
  }


  public String getSenderGpsLong() {
    return senderGpsLong;
  }

  public void setSenderGpsLong(String senderGpsLong) {
    this.senderGpsLong = senderGpsLong;
  }


  public String getSenderGpsLat() {
    return senderGpsLat;
  }

  public void setSenderGpsLat(String senderGpsLat) {
    this.senderGpsLat = senderGpsLat;
  }


  public String getReceiverGpsLong() {
    return receiverGpsLong;
  }

  public void setReceiverGpsLong(String receiverGpsLong) {
    this.receiverGpsLong = receiverGpsLong;
  }


  public String getReceiverGpsLat() {
    return receiverGpsLat;
  }

  public ShippingNeed setReceiverGpsLat(String receiverGpsLat) {
    this.receiverGpsLat = receiverGpsLat;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public long getIsPellet() {
    return isPellet;
  }

  public void setIsPellet(long isPellet) {
    this.isPellet = isPellet;
  }


  public long getIsDelicate() {
    return isDelicate;
  }

  public void setIsDelicate(long isDelicate) {
    this.isDelicate = isDelicate;
  }


  public long getNeedChilled() {
    return needChilled;
  }

  public void setNeedChilled(long needChilled) {
    this.needChilled = needChilled;
  }


  public double getBoxSizeX() {
    return boxSizeX;
  }

  public void setBoxSizeX(double boxSizeX) {
    this.boxSizeX = boxSizeX;
  }


  public double getBoxSizeY() {
    return boxSizeY;
  }

  public void setBoxSizeY(double boxSizeY) {
    this.boxSizeY = boxSizeY;
  }


  public double getBoxSizeZ() {
    return boxSizeZ;
  }

  public void setBoxSizeZ(double boxSizeZ) {
    this.boxSizeZ = boxSizeZ;
  }


  public double getBoxWeight() {
    return boxWeight;
  }

  public void setBoxWeight(double boxWeight) {
    this.boxWeight = boxWeight;
  }


  public long getNumberOfBoxes() {
    return numberOfBoxes;
  }

  public void setNumberOfBoxes(long numberOfBoxes) {
    this.numberOfBoxes = numberOfBoxes;
  }


  public double getBudget() {
    return budget;
  }

  public void setBudget(double budget) {
    this.budget = budget;
  }

}
