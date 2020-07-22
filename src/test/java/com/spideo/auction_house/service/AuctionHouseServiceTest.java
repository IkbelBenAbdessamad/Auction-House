package com.spideo.auction_house.service;

import com.spideo.auction_house.domain.*;
import com.spideo.auction_house.repository.AuctionBidderRepository;
import com.spideo.auction_house.repository.AuctionHouseRepository;
import com.spideo.auction_house.repository.AuctionRepository;
import com.spideo.auction_house.repository.BidRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional(propagation = Propagation.REQUIRES_NEW)
class AuctionHouseServiceTest {

    @Autowired
    private AuctionHouseRepository auctionHouseRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private AuctionBidderRepository auctionBidderRepository;

    @Autowired
    private BidRepository bidRepository;

    private static AuctionHouse auctionHouseTest;
    private static Auction auctionTest1;
    private static Auction auctionTest2;
    private static Auction auctionTest3;
    private static AuctionBidder auctionBidderTest1;
    private static AuctionBidder auctionBidderTest2;
    private static Bid bidTest;
    private static Long bidPrice1;
    private static Long bidPrice2;

    @BeforeAll
    static void init() throws ParseException {

        Instant startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SS").parse("2020-07-18T09:00:34:00:10").toInstant();
        Instant endTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SS").parse("2020-07-20T09:00:34:00:50").toInstant();
        auctionHouseTest = new AuctionHouse(null, "AuctionHouse1", new HashSet<>(0));
        auctionTest1 = new Auction(null, "Auction1", "Auction1 description", startTime, endTime, 2000L, 2200L, AuctionStatus.RUNNING, null, new ArrayList<>(0));
        auctionTest2 = new Auction(null, "Auction2", "Auction2 description", startTime, endTime, 3500L, 3700L, AuctionStatus.NOT_STARTED, null, new ArrayList<>(0));
        auctionTest3 = new Auction(null, "Auction3", "Auction3 description", startTime, endTime, 1500L, 1700L, AuctionStatus.TERMINATED, null, new ArrayList<>(0));
        auctionBidderTest1 = new AuctionBidder(null, "Alice", null);
        auctionBidderTest2 = new AuctionBidder(null, "Bob", null);
        bidTest = new Bid();
        bidPrice1 = 2300L;
        bidPrice2 = 3790L;


    }


    /**
     * STEP1 TESTS
     */

    @Test
    void createAuctionHouse() {
        AuctionHouse auctionHouse = auctionHouseRepository.save(auctionHouseTest);
        Assertions.assertNotNull(auctionHouse.getId());
        Assertions.assertEquals(auctionHouse.getAuctionHouseName(), auctionHouseTest.getAuctionHouseName());
    }


    @Test
    void findAllAuctionHouses() {
        AuctionHouse auctionHouse = auctionHouseRepository.save(auctionHouseTest);
        Assertions.assertNotNull(auctionHouseRepository.findAll());
        Assertions.assertEquals(1, auctionHouseRepository.count());
    }

    @Test
    void deleteAuctionHouse() {
        AuctionHouse auctionHouse = auctionHouseRepository.save(auctionHouseTest);
        Assertions.assertEquals(1, auctionHouseRepository.count());
        auctionHouseRepository.delete(auctionHouse);
        Assertions.assertEquals(0, auctionHouseRepository.count());
    }

    /**
     * STEP2 TESTS
     */

    @Test
    void createAuction() {
        AuctionHouse auctionHouse = auctionHouseRepository.save(auctionHouseTest);
        auctionHouse.addAuction(auctionTest1);
        auctionHouseRepository.save(auctionHouse);
        Assertions.assertEquals(1, auctionRepository.count());
    }

    @Test
    void getAuctionsByAuctionHouseId() {

        AuctionHouse auctionHouse = auctionHouseRepository.save(auctionHouseTest);
        auctionHouse.addAuction(auctionTest1);
        auctionHouse.addAuction(auctionTest2);
        auctionHouseRepository.save(auctionHouse);
        Assertions.assertEquals(2, auctionRepository.count());
        Assertions.assertEquals(2, auctionHouse.getAuctionList().size());
    }

    @Test
    void deleteAuctionById() {
        AuctionHouse auctionHouse = auctionHouseRepository.save(auctionHouseTest);
        auctionHouse.addAuction(auctionTest1);
        auctionHouseRepository.save(auctionHouse);
        Assertions.assertEquals(1, auctionRepository.count());
        Auction deletedAuction = new Auction();

        for (Auction a : auctionHouse.getAuctionList()) {
            if (a.getName().equals(auctionTest1.getName())) {
                deletedAuction = a;
                break;
            }
        }

        auctionHouse.removeAuction(deletedAuction);
        auctionHouseRepository.save(auctionHouse);
        Assertions.assertEquals(0, auctionRepository.count());

    }

    @Test
    void listAuctionsByStatus() {
        AuctionHouse auctionHouse = auctionHouseRepository.save(auctionHouseTest);
        auctionHouse.addAuction(auctionTest1);
        auctionHouse.addAuction(auctionTest2);
        auctionHouseRepository.save(auctionHouse);

        Assertions.assertEquals(1, auctionRepository.findAllByAuctionStatus(AuctionStatus.NOT_STARTED).size());
        Assertions.assertEquals(1, auctionRepository.findAllByAuctionStatus(AuctionStatus.RUNNING).size());
    }

    /**
     * STEP3 TESTS
     */
    @Test
    void createAuctionBidder() {
        AuctionBidder auctionBidder = auctionBidderRepository.save(auctionBidderTest1);
        Assertions.assertNotNull(auctionBidder.getId());

    }

    @Test

        // keeps the session open till the end of the execution.
    void bidAnAuction() {
        AuctionHouse auctionHouse = auctionHouseRepository.save(auctionHouseTest);
        auctionHouse.addAuction(auctionTest1);
        auctionHouseRepository.save(auctionHouse);

        Auction auction = new Auction();
        for (Auction a : auctionHouse.getAuctionList()) {
            if (a.getName().equals(auctionTest1.getName())) {
                auction = a;
                break;
            }
        }

        AuctionBidder auctionBidder = auctionBidderRepository.save(auctionBidderTest1);

        Assertions.assertEquals(AuctionStatus.RUNNING, auction.getAuctionStatus());
        Assertions.assertTrue(auction.getCurrent_price() < bidPrice1);


        auction.setCurrent_price(bidPrice1);
        auctionRepository.save(auction);

        bidTest.setAuction(auction);
        bidTest.setAuctionBidder(auctionBidder);
        bidTest.setBidderFullName(auctionBidder.getBidderFullName());
        bidTest.setBidPrice(bidPrice1);

        auction.addBid(bidTest);

        auctionRepository.save(auction);

        Assertions.assertEquals(1, bidRepository.count());

    }

    @Test
    void listAllBiddingForAuction() {
        AuctionHouse auctionHouse = auctionHouseRepository.save(auctionHouseTest);
        auctionHouse.addAuction(auctionTest1);
        auctionHouseRepository.save(auctionHouse);

        Auction auction = auctionRepository.findById(1L).get();

        AuctionBidder auctionBidder = auctionBidderRepository.save(auctionBidderTest1);

        Bid bid1 = new Bid(bidPrice1, auction, auctionBidder, auctionBidder.getBidderFullName());
        Bid bid2 = new Bid(bidPrice2, auction, auctionBidder, auctionBidder.getBidderFullName());


        auction.addBid(bid1);
        auction.addBid(bid2);
        auctionRepository.save(auction);

        Assertions.assertEquals(2, bidRepository.count());

    }


    @Test
    void showWinnerForAuction() {

        AuctionHouse auctionHouse = auctionHouseRepository.save(auctionHouseTest);
        auctionHouse.addAuction(auctionTest3);
        auctionHouseRepository.save(auctionHouse);
        Auction auction = new Auction();

        for (Auction a : auctionHouse.getAuctionList()) {
            if (a.getName().equals(auctionTest3.getName())) {
                auction = a;
                break;
            }
        }
        AuctionBidder bidder1 = auctionBidderRepository.save(auctionBidderTest1);
        AuctionBidder bidder2 = auctionBidderRepository.save(auctionBidderTest2);

        Assertions.assertEquals(AuctionStatus.TERMINATED, auction.getAuctionStatus());

        Bid bid1 = new Bid(bidPrice1, auction, bidder1, bidder1.getBidderFullName());
        Bid bid2 = new Bid(bidPrice2, auction, bidder2, bidder2.getBidderFullName());

        auction.addBid(bid1);
        auction.addBid(bid2);
        auction.setCurrent_price(bidPrice1 > bidPrice2 ? bidPrice1 : bidPrice2);
        auctionRepository.save(auction);

        auction.setAuctionStatus(AuctionStatus.TERMINATED);
        auctionHouse.addAuction(auction);
        auctionHouseRepository.save(auctionHouse);

        Assertions.assertEquals(AuctionStatus.TERMINATED, auction.getAuctionStatus());
        Assertions.assertEquals(2, bidRepository.count());
        Assertions.assertEquals(auction.getCurrent_price(), bidPrice2);

    }
}