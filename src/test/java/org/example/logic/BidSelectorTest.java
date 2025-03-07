package org.example.logic;

import org.example.entities.Bid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BidSelectorTest {
    private BidScoreCalculator mockScoreCalculator;
    private BidSelector bidSelector;



    @BeforeEach
    void setUp() {
        mockScoreCalculator = mock(BidScoreCalculator.class); // Mock the score calculator
        bidSelector = new BidSelector(mockScoreCalculator);
    }

    @Test
    void testNoBids() {
        assertEquals("No Winner", bidSelector.selectWinningBid(List.of()),
                "Should return 'No Winner' when no bids are present.");
    }

    @Test
    void testSingleBid() {
        Bid bid = new Bid("PublisherA", 100, 1.5);

        when(mockScoreCalculator.calculateScore(bid)).thenReturn(150.0);

        assertEquals("PublisherA", bidSelector.selectWinningBid(List.of(bid)),
                "A single bid should always win.");
    }

    @Test
    void testMultipleBidsWithDifferentScores() {
        Bid bid1 = new Bid("PublisherA", 100, 1.5);
        Bid bid2 = new Bid("PublisherB", 200, 1.0);
        Bid bid3 = new Bid("PublisherC", 150, 1.5);

        when(mockScoreCalculator.calculateScore(bid1)).thenReturn(150.0);
        when(mockScoreCalculator.calculateScore(bid2)).thenReturn(200.0);
        when(mockScoreCalculator.calculateScore(bid3)).thenReturn(225.0);

        assertEquals("PublisherC", bidSelector.selectWinningBid(Arrays.asList(bid1, bid2, bid3)),
                "Bid with the highest score should win.");
    }

    @Test
    void testTieBreakingByBidAmount() {
        Bid bid1 = new Bid("PublisherA", 100, 2.0); // Score = 200
        Bid bid2 = new Bid("PublisherB", 200, 1.0); // Score = 200, but higher bidAmount

        when(mockScoreCalculator.calculateScore(bid1)).thenReturn(200.0);
        when(mockScoreCalculator.calculateScore(bid2)).thenReturn(200.0);

        assertEquals("PublisherB", bidSelector.selectWinningBid(Arrays.asList(bid1, bid2)),
                "When scores are tied, the bid with the higher amount should win.");
    }

    @Test
    void testTieBreakingByOrder() {
        Bid bid1 = new Bid("PublisherA", 150, 1.5); // Score = 225
        Bid bid2 = new Bid("PublisherB", 150, 1.5); // Score = 225 (same as first bid)

        when(mockScoreCalculator.calculateScore(bid1)).thenReturn(225.0);
        when(mockScoreCalculator.calculateScore(bid2)).thenReturn(225.0);

        assertEquals("PublisherA", bidSelector.selectWinningBid(Arrays.asList(bid1, bid2)),
                "When scores and bid amounts are identical, the first bid should be selected.");
    }

    @Test
    void testLargeNumbers() {
        Bid bid1 = new Bid("PublisherA", 1_000_000, 2.0);
        Bid bid2 = new Bid("PublisherB", 500_000, 4.5);

        when(mockScoreCalculator.calculateScore(bid1)).thenReturn(2_000_000.0);
        when(mockScoreCalculator.calculateScore(bid2)).thenReturn(2_250_000.0);

        assertEquals("PublisherB", bidSelector.selectWinningBid(Arrays.asList(bid1, bid2)),
                "Should correctly handle large bid values.");
    }
}
