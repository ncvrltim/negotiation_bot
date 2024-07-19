package com.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "car")
public class Car {
    @Id
    @Column(name = "car_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "make")
    private String make;
    @Column(name = "model")
    private String model;
    @Column(name = "car_year")
    private int year;
    @Column(name = "minBidAmount")
    private double minBidAmount;
    @Column(nullable = true)
    private Boolean soldOut; // Indicates if the car is available for bidding
    @Column(nullable = true)
    private Double acceptedBidAmount; // Stores the accepted bid amount
    @JoinColumn(nullable = true)
    @ManyToOne
    private User buyer; // Stores the buyer associated with the accepted bid
    @Column(name= "terms" , nullable = true, length = 2000)
    private String terms;

    
    
}
