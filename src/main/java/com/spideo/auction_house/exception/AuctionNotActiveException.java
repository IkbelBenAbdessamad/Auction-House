package com.spideo.auction_house.exception;

public class AuctionNotActiveException extends Exception {


    public AuctionNotActiveException() {
        super("Auction not active yet, it  could be deleted, terminated or not started");
    }
}
