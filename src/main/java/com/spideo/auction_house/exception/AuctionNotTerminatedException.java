package com.spideo.auction_house.exception;

public class AuctionNotTerminatedException extends Exception {

    public AuctionNotTerminatedException() {
        super("Auction is active cannot return the  winner");
    }
}
