package com.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "bid")
public class Bid {

    @Id
    @Column(name = "bid_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Car car;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User buyer;

    @Column(name = "bidAmount")
    private double bidAmount;

    @Column(name = "maxBidAmount")
    private double maxBidAmount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BidStatus status; // PENDING, ACCEPTED, REJECTED

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
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

	public BidStatus getStatus() {
		return status;
	}

	public void setStatus(BidStatus status) {
		this.status = status;
	}

    // Other bid details


}
