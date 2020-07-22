package com.spideo.auction_house.repository;

import com.spideo.auction_house.domain.Auction;
import com.spideo.auction_house.domain.AuctionHouse;
import com.spideo.auction_house.domain.AuctionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    List<Auction> findAllByAuctionHouse(AuctionHouse auctionHouse);

    List<Auction> findAllByAuctionStatus(AuctionStatus auctionStatus);
}
