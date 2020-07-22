package com.spideo.auction_house.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Details about the auction house")
public class AuctionHouse {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String auctionHouseName;

    /**
     * The 'mappedBy = "auctionHouse"' attribute specifies that
     * the ' private AuctionHouse auctionHouse;' field in Auction class owns the
     * relationship (i.e. contains the foreign key for the query to
     * find all Auction for an AuctionHouse.)
     */
    @OneToMany(mappedBy = "auctionHouse", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Auction> auctionList = new HashSet<Auction>();

    public void addAuction(Auction auction) {
        auction.setAuctionHouse(this);
        auctionList.add(auction);
    }

    public void removeAuction(Auction auction) {
        auctionList.remove(auction);
        auction.setAuctionHouse(null);


    }

}
