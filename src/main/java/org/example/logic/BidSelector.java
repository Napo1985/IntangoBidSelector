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
        try {
            LOGGER.info("▶ START: selectWinningBid");

            if (bids == null || bids.isEmpty()) {
                LOGGER.warning("No bids available.");
                return "No Winner";
            }

            LOGGER.info(String.format("Starting bid selection process | Total bids: %d", bids.size()));

            Bid winningBid = null;
            double highestScore = Double.MIN_VALUE;

            for (Bid bid : bids) {
                double score = bidScoreCalculator.calculateScore(bid);
                boolean isNewWinner = (winningBid == null || score > highestScore ||
                        (score == highestScore && bid.getBidAmount() > winningBid.getBidAmount()));

                LOGGER.info(String.format("Processing %s | Score: %.2f%s",
                        bid, score, isNewWinner ? " | New winner" : ""));

                if (isNewWinner) {
                    winningBid = bid;
                    highestScore = score;
                }
            }

            LOGGER.info(String.format("Winning bid: %s", winningBid.getPublisherId()));
            LOGGER.info("End of bid selection process");
            return winningBid.getPublisherId();
        } catch (Exception ex) {
            LOGGER.severe("Exception message: " + ex.getMessage());
            throw new RuntimeException("Error with finding winning bid");
        } finally {
            LOGGER.info("⏹ END: selectWinningBid");
        }
    }
}
