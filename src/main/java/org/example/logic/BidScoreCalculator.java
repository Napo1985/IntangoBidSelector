package org.example.logic;

import org.example.entities.Bid;

public class BidScoreCalculator {
    public double calculateScore(Bid bid) {
        return bid.getBidAmount() * bid.getQualityScore();
    }
}
