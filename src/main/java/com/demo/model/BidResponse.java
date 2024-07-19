package com.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BidResponse {

    private boolean accepted;
    private double counterOfferAmount;
    private String message;
    private String negotiationStage;
    private String negotiationDetails;
    private LocalDateTime timestamp;
	public boolean isAccepted() {
		return accepted;
	}
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	public double getCounterOfferAmount() {
		return counterOfferAmount;
	}
	public void setCounterOfferAmount(double counterOfferAmount) {
		this.counterOfferAmount = counterOfferAmount;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNegotiationStage() {
		return negotiationStage;
	}
	public void setNegotiationStage(String negotiationStage) {
		this.negotiationStage = negotiationStage;
	}
	public String getNegotiationDetails() {
		return negotiationDetails;
	}
	public void setNegotiationDetails(String negotiationDetails) {
		this.negotiationDetails = negotiationDetails;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
    
    

}
