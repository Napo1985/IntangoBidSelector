package org.example.logic;

import org.example.entities.Bid;
import org.example.infra.LoggerSingleton;

import java.util.List;
import java.util.logging.Logger;

public class BidSelector {
    private static final Logger LOGGER = LoggerSingleton.getLogger();
    private final BidScoreCalculator bidScoreCalculator;

    public BidSelector(BidScoreCalculator bidScoreCalculator) {
        this.bidScoreCalculator = bidScoreCalculator;
    }

    public String selectWinningBid(List<Bid> bids) {
        if (bids == null || bids.isEmpty()) {
            LOGGER.warning("No bids available.");
            return "No Winner";
        }
        StringBuilder selectionLog = new StringBuilder(); // Collect logs

        Bid winningBid = null;
        double highestScore = Double.MIN_VALUE;

        for (Bid bid : bids) {
            double score = bidScoreCalculator.calculateScore(bid);
            selectionLog.append(String.format("Processing %s | Score: %.2f%n", bid, score));

            if (winningBid == null ||
                    score > highestScore ||
                    (score == highestScore && bid.getBidAmount() > winningBid.getBidAmount())) {

                selectionLog.append(String.format("NEW WINNER FOUND: %s%n", bid));
                winningBid = bid;
                highestScore = score;
            }
        }

        selectionLog.append(String.format("Winning bid: %s%n", winningBid));
        LOGGER.fine(selectionLog.toString());
        return winningBid.getPublisherId();
    }
}
