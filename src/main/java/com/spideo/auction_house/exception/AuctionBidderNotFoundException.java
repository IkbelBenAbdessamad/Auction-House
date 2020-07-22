package com.spideo.auction_house.exception;

public class AuctionBidderNotFoundException extends Exception {

    public AuctionBidderNotFoundException() {
        super("The entered auction bidder  doesn't exist");
    }
}
