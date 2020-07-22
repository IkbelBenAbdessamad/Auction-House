package com.spideo.auction_house.service;

import com.spideo.auction_house.domain.*;
import com.spideo.auction_house.exception.*;
import com.spideo.auction_house.repository.AuctionBidderRepository;
import com.spideo.auction_house.repository.AuctionHouseRepository;
import com.spideo.auction_house.repository.AuctionRepository;
import com.spideo.auction_house.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AuctionHouseService {

    @Autowired
    private AuctionHouseRepository auctionHouseRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private AuctionBidderRepository auctionBidderRepository;

    @Autowired
    private BidRepository bidRepository;


    /**
     * STEP1
     **/
    public AuctionHouse createAuctionHouse(String auctionHouseName) {
        if (auctionHouseName.isEmpty()) throw new IllegalArgumentException("Auction name is empty");
        AuctionHouse newAuctionHouse = new AuctionHouse();
        newAuctionHouse.setAuctionHouseName(auctionHouseName);
        return auctionHouseRepository.save(newAuctionHouse);
    }


    public List<AuctionHouse> findAllAuctionHouses() {
        return auctionHouseRepository.findAll();
    }

    public void deleteAuctionHouse(Long auctionHouseId) throws AuctionHouseNotFoundException {
        AuctionHouse auctionHouse = auctionHouseRepository.findById(auctionHouseId)
                .orElseThrow(AuctionHouseNotFoundException::new);
        auctionHouseRepository.delete(auctionHouse);
    }

    /**
     * STEP2
     **/

    public void createAuction(Long auctionHouseId, Auction auction) throws AuctionHouseNotFoundException {
        //TODO add exceptions handlers
        //TODO test if  the entered staring-time  is  bigger  than  the current Datetime
        AuctionHouse auctionHouse = auctionHouseRepository.findById(auctionHouseId).orElseThrow(AuctionHouseNotFoundException::new);
        auctionHouse.addAuction(auction);
        auctionHouseRepository.save(auctionHouse);
    }

    public List<Auction> getAuctionsByAuctionHouseId(Long id) throws AuctionHouseNotFoundException {

        AuctionHouse auctionHouse = auctionHouseRepository.findById(id).orElseThrow(AuctionHouseNotFoundException::new);
        return auctionRepository.findAllByAuctionHouse(auctionHouse);
    }

    public void deleteAuctionById(Long auctionHouseId, Long auctionId) throws AuctionHouseNotFoundException, AuctionNotFoundException {
        AuctionHouse auctionHouse = auctionHouseRepository.findById(auctionHouseId).orElseThrow(AuctionHouseNotFoundException::new);
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(AuctionNotFoundException::new);
        auctionHouse.removeAuction(auction);
        auctionHouseRepository.save(auctionHouse);
    }

    public List<Auction> listAuctionsByStatus(Long auctionHouseId, AuctionStatus auctionStatus) throws AuctionHouseNotFoundException {
        AuctionHouse auctionHouse = auctionHouseRepository.findById(auctionHouseId).orElseThrow(AuctionHouseNotFoundException::new);

        return auctionRepository.findAllByAuctionHouse(auctionHouse)
                .stream()
                .filter(auction -> auction.getAuctionStatus().equals(auctionStatus))
                .collect(Collectors.toList());
    }

    public Auction updateAuctionStatus(Long auctionHouseId, Long auctionId, AuctionStatus newStatus) throws AuctionHouseNotFoundException, AuctionNotFoundException {

        AuctionHouse auctionHouse = auctionHouseRepository.findById(auctionHouseId).orElseThrow(AuctionHouseNotFoundException::new);
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(AuctionNotFoundException::new);
        auction.setAuctionStatus(newStatus);
        auctionHouse.addAuction(auction);
        auctionHouseRepository.save(auctionHouse);

        return auctionRepository.save(auction);
    }


    /**
     * Step3
     */

    public AuctionBidder createAuctionBidder(AuctionBidder auctionBidder) {
        if (auctionBidder.equals(null)) throw new IllegalArgumentException("auctionBidder object is Null");
        return auctionBidderRepository.save(auctionBidder);
    }


    public void bidAnAuction(Long auctionHouseId, Long auctionId, Long auctionBidderId, Long bidPrice) throws AuctionHouseNotFoundException, AuctionNotFoundException, AuctionBidderNotFoundException, AuctionNotActiveException, BiddingPriceLowerException {

        AuctionHouse auctionHouse = auctionHouseRepository.findById(auctionHouseId).orElseThrow(AuctionHouseNotFoundException::new);
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(AuctionNotFoundException::new);
        AuctionBidder auctionBidder = auctionBidderRepository.findById(auctionBidderId).orElseThrow(AuctionBidderNotFoundException::new);
        if (auction.getAuctionStatus() != AuctionStatus.RUNNING) throw new AuctionNotActiveException();
        if (auction.getCurrent_price().compareTo(bidPrice) > -1) throw new BiddingPriceLowerException();

        Bid bid = new Bid(bidPrice, auction, auctionBidder, auctionBidder.getBidderFullName());
        auction.setCurrent_price(bidPrice);
        auction.addBid(bid);
        auctionRepository.save(auction);

    }

    public List<Bid> listAllBiddingForAuction(Long auctionHouseId, Long auctionId) throws AuctionHouseNotFoundException, AuctionNotFoundException {

        AuctionHouse auctionHouse = auctionHouseRepository.findById(auctionHouseId).orElseThrow(AuctionHouseNotFoundException::new);
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(AuctionNotFoundException::new);
        return bidRepository.findAllByAuction(auction);
    }

    public AuctionBidder showWinnerForAuction(Long auctionHouseId, Long auctionId) throws AuctionNotFoundException, AuctionHouseNotFoundException, AuctionNotTerminatedException {

        AuctionHouse auctionHouse = auctionHouseRepository.findById(auctionHouseId).orElseThrow(AuctionHouseNotFoundException::new);
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(AuctionNotFoundException::new);

        if (!auction.getAuctionStatus().equals(AuctionStatus.TERMINATED)) throw new AuctionNotTerminatedException();
        List<Bid> listOfBidding = auction.getBidList();
        return listOfBidding.get(listOfBidding.size() - 1).getAuctionBidder();
    }


}
