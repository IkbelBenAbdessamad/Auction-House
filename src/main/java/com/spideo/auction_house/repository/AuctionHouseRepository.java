package com.spideo.auction_house.repository;

import com.spideo.auction_house.domain.AuctionHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionHouseRepository extends JpaRepository<AuctionHouse, Long> {


}
