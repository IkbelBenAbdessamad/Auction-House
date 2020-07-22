package com.spideo.auction_house.controller;


import com.spideo.auction_house.domain.*;
import com.spideo.auction_house.exception.*;
import com.spideo.auction_house.service.AuctionHouseService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auction-house")
@Slf4j
public class AuctionHouseController {

    @Autowired
    private AuctionHouseService auctionHouseService;

    /**
     * STEP 1
     */
    @PostMapping(value = "/createAuctionHouse/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Creates an AuctionHouse by name (REQUEST 1)", notes = "Provide a name to create a new auction house", position = 0)
    public AuctionHouse createAuctionHouse(@PathVariable String name) {
        log.debug("Create a new  auction house named as {} ", name);
        return auctionHouseService.createAuctionHouse(name);
    }

    @GetMapping("/listAll")
    @ApiOperation(value = "Lists auction houses (REQUEST 2)", notes = "List all the created  auction houses", position = 1

    )
    public List<AuctionHouse> listAllAuctionHouses() {
        log.debug("List all auction houses created");
        return auctionHouseService.findAllAuctionHouses();
    }


    @DeleteMapping("/{auctionHouseId}")
    @ApiOperation(value = "Deletes an auctionHouse by ID (REQUEST 3)", notes = "Provide an id to delete an auction house", position = 2)
    public ResponseEntity<?> deleteAuctionHouseById(@PathVariable Long auctionHouseId) throws AuctionHouseNotFoundException {
        log.debug("Delete auction house with ID", auctionHouseId);
        auctionHouseService.deleteAuctionHouse(auctionHouseId);
        return ResponseEntity.ok("Auction house deleted successfully !");
    }

    /**
     * STEP 2 For a given Auction house:
     */

    @PostMapping(value = "/{auctionHouseId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Creates an auction (REQUEST 4)",
            notes = "For  a  given auction house create a new auction passed  in the  request body as a  json object", position = 3)
    public void createAuction(@PathVariable Long auctionHouseId, @Validated @RequestBody Auction auction) throws AuctionHouseNotFoundException {
        log.debug("For a given auction house create a new auction ");
        auctionHouseService.createAuction(auctionHouseId, auction);

    }

    @GetMapping("/{auctionHouseId}")
    @ApiOperation(value = "Lists all created auctions by auction house ID (REQUEST 5)", notes = "For  a  given auction house  list all auctions", position = 4)
    public List<Auction> listAllAuctionsForAuctionHouseId(@PathVariable Long auctionHouseId) throws AuctionHouseNotFoundException {
        log.debug("List all the  created  auction for  auction house with ID", auctionHouseId);
        return auctionHouseService.getAuctionsByAuctionHouseId(auctionHouseId);
    }


    @DeleteMapping("/{auctionHouseId}/{auctionId}")
    @ApiOperation(value = "Deletes an auction by  ID (REQUEST 6)", notes = "For  a  given auction house delete a specific auction by its ID", position = 5)
    public ResponseEntity<?> deleteAuctionById(@PathVariable Long auctionHouseId, @PathVariable Long auctionId) throws AuctionNotFoundException, AuctionHouseNotFoundException {
        log.debug("Delete Auction with ID {} from Auction house with ID {}", auctionId, auctionHouseId);
        auctionHouseService.deleteAuctionById(auctionHouseId, auctionId);
        return ResponseEntity.ok("Auction deleted successfully !");
    }

    @GetMapping("/{auctionHouseId}/{auctionStatus}")
    @ApiOperation(value = "Lists auctions based on their status (REQUEST 7)", notes = "For  a  given auction house list auctions  based on the entered status", position = 6)
    public List<Auction> listAuctionsByStatus(@PathVariable Long auctionHouseId, @PathVariable AuctionStatus auctionStatus) throws AuctionHouseNotFoundException {
        log.debug("List all auctions with status", auctionStatus);
        return auctionHouseService.listAuctionsByStatus(auctionHouseId, auctionStatus);
    }

    @PutMapping("/{auctionHouseId}/{auctionId}/{newStatus}")
    @ApiOperation(value = "Updates an auction with a new status (REQUEST 8)", notes = "For  a  given auction house update the status of an auction  ", position = 7)
    public ResponseEntity<Auction> updateAuctionStatus(@PathVariable Long auctionHouseId, @PathVariable Long auctionId, @PathVariable AuctionStatus newStatus) throws AuctionNotFoundException,

            AuctionHouseNotFoundException {
        log.debug("Updated  the status of an existed auction");
        Auction updatedAuction = auctionHouseService.updateAuctionStatus(auctionHouseId, auctionId, newStatus);
        return ResponseEntity.ok(updatedAuction);
    }

    /**
     * STEP 3  For a  given  auction:
     */

    @PostMapping("/createAuctionBidder")
    @ApiOperation(value = "Creates an auction bidder (REQUEST 9)", notes = "Create a new auction bidder", position = 8)
    public ResponseEntity<AuctionBidder> createAuctionBidder(@RequestBody AuctionBidder auctionBidder) {
        log.debug("Create a new  auction bidder");
        AuctionBidder newAuctionBidder = auctionHouseService.createAuctionBidder(auctionBidder);
        return ResponseEntity.ok(newAuctionBidder);

    }

    @PostMapping("{auctionHouseId}/{auctionId}/{auctionBidderId}/{bidPrice}")
    @ApiOperation(value = "Bids an auction with a higher price (REQUEST 10)", notes = "For a given auction house and a  given auction let a given bidder bid an auction with a  higher  price", position = 9)
    public ResponseEntity<?> bidAnAuction(@PathVariable Long auctionHouseId, @PathVariable Long auctionId, @PathVariable Long
            auctionBidderId, @PathVariable Long bidPrice) throws
            AuctionNotActiveException, AuctionBidderNotFoundException, AuctionNotFoundException, BiddingPriceLowerException, AuctionHouseNotFoundException {
        log.debug("Let bidder {} bid auction {} in auction house {}", auctionBidderId, auctionId, auctionHouseId);
        auctionHouseService.bidAnAuction(auctionHouseId, auctionId, auctionBidderId, bidPrice);
        return ResponseEntity.ok("A new bid for  auctionId = " + auctionId + " and auctionBidder id = " + auctionBidderId + " is created successfully !");

    }

    @GetMapping("listAllBidding/{auctionHouseId}/{auctionId}")
    @ApiOperation(value = "List all bidding for an auction (REQUEST 11)",
            notes = "For a given auction house and a given auction, list all bidding with the username that has happen until now", position = 10)
    public List<Bid> listAllBiddingForAuction(@PathVariable Long auctionHouseId, @PathVariable Long auctionId) throws
            AuctionNotFoundException, AuctionHouseNotFoundException {

        log.debug("List all bidding for  auction", auctionId);
        return auctionHouseService.listAllBiddingForAuction(auctionHouseId, auctionId);
    }


    @GetMapping("showWinner/{auctionHouseId}/{auctionId}")
    @ApiOperation(value = "Shows auction winner (REQUEST 12)", notes = "Show the winner for a  given auction that should be  TERMINATED", position = 11)
    public ResponseEntity<AuctionBidder> showWinnerForAuction(@PathVariable Long auctionHouseId, @PathVariable Long auctionId) throws
            AuctionHouseNotFoundException, AuctionNotTerminatedException, AuctionNotFoundException {
        log.debug("Show winner  for auction", auctionId);
        AuctionBidder winner = auctionHouseService.showWinnerForAuction(auctionHouseId, auctionId);
        return ResponseEntity.ok(winner);
    }

}
