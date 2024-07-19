package com.demo.controller;


import com.demo.entity.Bid;
import com.demo.entity.BidStatus;
import com.demo.model.BidRequest;
import com.demo.model.BidResponse;
import com.demo.repository.BidRepository;
import com.demo.repository.CarRepository;
import com.demo.repository.UserRepository;
import com.demo.service.BidProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bids")
public class BidController {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private BidProcessorService bidProcessorService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;
    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<String> placeBid(@RequestBody BidRequest bidRequest) {
        Bid bid = new Bid();
        bid.setStatus(BidStatus.PENDING);
        bid.setBidAmount(bidRequest.getBidAmount());
        bid.setMaxBidAmount(bidRequest.getMaxBidAmount());
        bid.setBuyer(userRepository.findById(bidRequest.getBuyerId()).get());
        bid.setCar(carRepository.findById(bidRequest.getCarId()).get());
        bidRepository.save(bid);
      //  bidProcessorService.processBids(); // Trigger bid processing
        return ResponseEntity.ok("Bid placed successfully.");
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<Bid>> getBidHistoryByBuyer(@PathVariable Long buyerId) {
        List<Bid> bidHistory = bidRepository.findBidsByBuyerId(buyerId);
        return ResponseEntity.ok(bidHistory);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/car/{carId}")
    public ResponseEntity<List<Bid>> getBidHistoryByCar(@PathVariable Long carId) {
        List<Bid> bidHistory = bidRepository.findBidsByCarId(carId);
        return ResponseEntity.ok(bidHistory);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{bidId}/status")
    public ResponseEntity<BidResponse> getBidStatus(@PathVariable Long bidId) {
        Optional<Bid> bidOptional = bidRepository.findById(bidId);
        if (bidOptional.isPresent()) {
            Bid bid = bidOptional.get();
            BidResponse response = constructBidResponse(bid);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    private BidResponse constructBidResponse(Bid bid) {
        if (bid.getStatus() == BidStatus.ACCEPTED) {
            return new BidResponse(true, bid.getBidAmount(), "Your offer has been accepted.",
                    "Initial Offer", "Buyer offered $" + bid.getBidAmount(), LocalDateTime.now());
        } else if (bid.getStatus() == BidStatus.REJECTED) {
            return new BidResponse(false, 0, "Your offer has been rejected.",
                    "Initial Offer", "Buyer offered $" + bid.getBidAmount(), LocalDateTime.now());
        } else {
            return new BidResponse(false, 0, "Your offer is still pending.",
                    "Initial Offer", "Buyer offered $" + bid.getBidAmount(), LocalDateTime.now());
        }
    }
}
