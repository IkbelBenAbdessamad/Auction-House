package com.spideo.auction_house.exception;

public class BiddingPriceLowerException extends Exception {


    public BiddingPriceLowerException() {
        super("The bidding price is Lower or equal to the auction current price");
    }
}
