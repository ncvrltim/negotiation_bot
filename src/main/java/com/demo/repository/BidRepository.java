package com.demo.repository;

import com.demo.entity.Bid;
import com.demo.entity.BidStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByStatus(BidStatus bidStatus);

   List<Bid> findBidsByBuyerId(long buyerId);

    List<Bid> findBidsByCarId(long carId);

 }
