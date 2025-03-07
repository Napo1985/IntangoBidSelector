package org.example.integration;

import org.example.entities.Bid;
import org.example.logic.BidScoreCalculator;
import org.example.logic.BidSelector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class BidSelectorIntegrationTest {
    private final BidScoreCalculator bidScoreCalculator = new BidScoreCalculator();
    private final BidSelector bidSelector = new BidSelector(bidScoreCalculator);

    @Test
    void testNoBids() {
        assertEquals("No Winner", bidSelector.selectWinningBid(Collections.emptyList()));
    }

    @Test
    void testSingleBid() {
        Bid bid = new Bid("PublisherA", 100, 1.5);
        assertEquals("PublisherA", bidSelector.selectWinningBid(List.of(bid)));
    }

    @Test
    void testMultipleBidsWithDifferentScores() {
        List<Bid> bids = Arrays.asList(
                new Bid("PublisherA", 100, 1.5),  // Score = 150
                new Bid("PublisherB", 200, 1.0),  // Score = 200
                new Bid("PublisherC", 150, 1.5),   // Score = 15
                new Bid("PublisherD", 150, 1.1),  // Score = 225
                new Bid("PublisherE", 150, 1.2),   // Score = 225
                new Bid("PublisherF", 10, 1.5)   // Score = 225 (winner)
        );

        assertEquals("PublisherC", bidSelector.selectWinningBid(bids));
    }

    @Test
    void testTieBreakingByBidAmount() {
        List<Bid> bids = Arrays.asList(
                new Bid("PublisherA", 100, 2.0),  // Score = 200
                new Bid("PublisherB", 200, 1.0)   // Score = 200, but higher bidAmount (wins)
        );

        assertEquals("PublisherB", bidSelector.selectWinningBid(bids));
    }

    @Test
    void testTieBreakingByOrder() {
        List<Bid> bids = Arrays.asList(
                new Bid("PublisherA", 150, 1.5),  // Score = 225
                new Bid("PublisherB", 150, 1.5)   // Score = 225 (same as first bid)
        );

        assertEquals("PublisherA", bidSelector.selectWinningBid(bids),
                "The first bid should be selected when scores and quality are identical.");
    }

    @Test
    void testLargeNumbers() {
        List<Bid> bids = Arrays.asList(
                new Bid("PublisherA", 1_000_000, 2.0),  // Score = 2,000,000
                new Bid("PublisherB", 500_000, 4.5)    // Score = 2,250,000 (wins)
        );

        assertEquals("PublisherB", bidSelector.selectWinningBid(bids));
    }
}
