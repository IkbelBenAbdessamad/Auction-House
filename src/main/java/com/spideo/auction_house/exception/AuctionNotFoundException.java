package com.spideo.auction_house.exception;

public class AuctionNotFoundException extends Exception {


    public AuctionNotFoundException() {
        super("The given auction is null");
    }
}
