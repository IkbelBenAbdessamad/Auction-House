package com.spideo.auction_house.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Details about the bidder")
public class AuctionBidder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bidderFullName;

    @OneToMany(mappedBy = "auctionBidder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Bid> bid;


}
