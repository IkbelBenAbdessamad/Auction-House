package com.spideo.auction_house.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@ApiModel(description = "Details about the bid")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bidPrice;

    private Instant biddingExactTime = Instant.now();

    @ManyToOne
    @JoinColumn(name = "AUCTION_ID")
    @JsonIgnore
    private Auction auction;


    @ManyToOne
    @JoinColumn(name = "AUCTION_BIDDER_ID")
    @JsonIgnore
    private AuctionBidder auctionBidder;

    private String bidderFullName;

    public Bid(Long bidPrice, Auction auction, AuctionBidder auctionBidder, String bidderFullName) {
        this.bidPrice = bidPrice;
        this.auction = auction;
        this.auctionBidder = auctionBidder;
        this.bidderFullName = bidderFullName;
    }
}
