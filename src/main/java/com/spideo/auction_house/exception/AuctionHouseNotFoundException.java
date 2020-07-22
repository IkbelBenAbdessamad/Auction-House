package com.spideo.auction_house.exception;

public class AuctionHouseNotFoundException extends Exception {
    

    public AuctionHouseNotFoundException() {
        super("Auction house with the given Id doesn't exist");
    }
}
