package com.demo.model;

import lombok.Data;

@Data
public class BidRequest {
	
	private long carId;
	private long buyerId;
	private double bidAmount;
	private double maxBidAmount;
	public long getCarId() {
		return carId;
	}
	public void setCarId(long carId) {
		this.carId = carId;
	}
	public long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}
	public double getBidAmount() {
		return bidAmount;
	}
	public void setBidAmount(double bidAmount) {
		this.bidAmount = bidAmount;
	}
	public double getMaxBidAmount() {
		return maxBidAmount;
	}
	public void setMaxBidAmount(double maxBidAmount) {
		this.maxBidAmount = maxBidAmount;
	}



}
