package org.example;

import org.example.entities.Bid;
import org.example.infra.LoggerSingleton;
import org.example.logic.BidScoreCalculator;
import org.example.logic.BidSelector;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER =  LoggerSingleton.getLogger();

    public static void main(String[] args) {
        simpleRunCase();
    }

    private static void simpleRunCase() {
        LOGGER.info("Starting Bid Selection Process...");

        // Create a list of bid objects
        List<Bid> bids = Arrays.asList(
                new Bid("PublisherA", 100, 1.5),  // Score = 150
                new Bid("PublisherB", 200, 1.0),  // Score = 200
                new Bid("PublisherC", 150, 1.5)   // Score = 225 (winning bid)
        );

        // Instantiate the calculator and selector
        BidScoreCalculator bidScoreCalculator = new BidScoreCalculator();
        BidSelector bidSelector = new BidSelector(bidScoreCalculator);

        // Select the winning bid
        String winningPublisher = bidSelector.selectWinningBid(bids);

        // Print the winner
        LOGGER.info("Winning Publisher: " + winningPublisher);
    }
}