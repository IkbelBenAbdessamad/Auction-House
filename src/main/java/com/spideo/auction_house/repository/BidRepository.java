package com.spideo.auction_house.repository;

import com.spideo.auction_house.domain.Auction;
import com.spideo.auction_house.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {

    List<Bid> findAllByAuction(Auction auction);
}
