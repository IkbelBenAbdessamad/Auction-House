package com.spideo.auction_house.repository;

import com.spideo.auction_house.domain.AuctionBidder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionBidderRepository extends JpaRepository<AuctionBidder, Long> {
}
