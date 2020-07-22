package com.spideo.auction_house.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Entity
@AllArgsConstructor
@Getter
@Setter
@ApiModel(description = "Details about the auction")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss:SS", timezone = "UTC+1")
    private Instant starting_time;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss:SS", timezone = "UTC+1")
    private Instant end_time;
    private Long start_price;
    private Long current_price;
    @Enumerated(value = EnumType.STRING)
    private AuctionStatus auctionStatus;

    /**
     * Specifies the Auction table does not contain an AuctionHouse column, but
     * an AUCTION_HOUSE_ID column which is a  reference to the auction house.
     */
    @ManyToOne
    @JoinColumn(name = "AUCTION_HOUSE_ID")
    @JsonIgnore
    private AuctionHouse auctionHouse = null;

    @OneToMany(mappedBy = "auction", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Bid> bidList = new ArrayList<Bid>();

    public void addBid(Bid bid) {
        bid.setAuction(this);
        bidList.add(bid);

    }

    public void removeBid(Bid bid) {
        this.bidList.remove(bid);
        bid.setAuction(null);
    }


}
