package com.demo.service;

import com.demo.entity.Bid;
import com.demo.entity.BidStatus;
import com.demo.model.BidResponse;
import com.demo.entity.Car;
import com.demo.repository.BidRepository;
import com.demo.repository.CarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BidProcessorService {

    public static final String NEGOTIATION_DETAILS = "Buyer offered $ %,.2f. Seller countered with $ %,.2f.";
    public static final String COUNTER_OFFER = "Counteroffer";
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BidRepository bidRepository;

    @Scheduled(fixedDelay = 60000) // Run every 60 seconds
    public void processScheduledBids() {
        processBids();
    }

    @Transactional
    public void processBids() {
        List<Bid> pendingBids = bidRepository.findByStatus(BidStatus.PENDING);

        for (Bid bid : pendingBids) {
            Car car = bid.getCar();
            if (bid.getBidAmount() < car.getMinBidAmount()) {
                bid.setStatus(BidStatus.REJECTED);
            } else {
                negotiateBid(bid);
            }
            bidRepository.save(bid);
            updateCarListing(bid);
        }
    }

    private void negotiateBid(Bid bid) {
        Car car = bid.getCar();
        if(car.getSoldOut()){
            bid.setStatus(BidStatus.REJECTED);
            return;
        }
        double initialOffer = calculateInitialOffer(car.getMinBidAmount());
        bid.setBidAmount(initialOffer);

        // Send initial offer to buyer and wait for response
        BidResponse response = sendOfferAndGetResponse(bid, initialOffer);

        if (isWithinAcceptableRange(response, initialOffer)) {
            bid.setStatus(BidStatus.ACCEPTED);
        } else {
            double counterOffer = calculateCounterOffer(initialOffer);
            response = sendOfferAndGetResponse(bid, counterOffer);

            if (response.isAccepted()) {
                bid.setBidAmount(counterOffer);
                bid.setStatus(BidStatus.ACCEPTED);
            } else {
                bid.setStatus(BidStatus.REJECTED);
            }
        }
    }

    private double calculateInitialOffer(double minBidAmount) {
        // Define your logic to calculate the initial offer based on negotiation rules
        // For example, you could set it as a percentage above the minimum bid amount
        return minBidAmount * 1.1; // 10% above minimum bid
    }

    private double calculateCounterOffer(double previousOffer) {
        // Define your logic to calculate a counteroffer based on the previous offer
        // For example, you could decrease the offer by a fixed amount or percentage
        return previousOffer * 0.9; // 10% below previous offer
    }

    private boolean isWithinAcceptableRange(BidResponse response, double offer) {
        // Define your logic to check if the buyer's response is within acceptable range
        // For example, you might compare the buyer's response with a certain range
        double acceptableRange = offer * 0.05; // 5% of the offer
        return Math.abs(response.getCounterOfferAmount() - offer) <= acceptableRange;
    }

    private void updateCarListing(Bid bid) {
        Car car = bid.getCar();
        if (bid.getStatus() == BidStatus.ACCEPTED) {
            car.setAcceptedBidAmount(bid.getBidAmount());
            car.setBuyer(bid.getBuyer());
            car.setSoldOut(true); // Assuming you want to mark the car as no longer available
        }
        // Update other car details as needed
        carRepository.save(car); // Save the updated car entity
    }

    /*
     * Simulate sending the offer to the buyer and receiving a response
     * This could involve an external API call or simulated response
     * For the sake of example, I'm creating a sample BidResponse here
     */
    private BidResponse sendOfferAndGetResponse(Bid bid, double offer) {
        if (offer <= bid.getMaxBidAmount()) {
            double originalOffer = bid.getBidAmount();
            bid.setBidAmount(offer);
            return new BidResponse(true, bid.getBidAmount(), "Your offer has been accepted.",
                    "Counteroffer", String.format(NEGOTIATION_DETAILS, offer, originalOffer),
                    LocalDateTime.now());
        } else {

            return new BidResponse(false, bid.getBidAmount(), "Your offer has been Rejected.",
                    COUNTER_OFFER, String.format(NEGOTIATION_DETAILS, offer, bid.getBidAmount()),
                    LocalDateTime.now());
        }
    }
}

