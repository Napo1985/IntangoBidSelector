package org.example.logic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.example.entities.Bid;

public class BidScoreCalculatorTest {
    private final BidScoreCalculator bidScoreCalculator = new BidScoreCalculator();

    @Test
    void testCalculateScore() {
        Bid bid = new Bid("PublisherA", 100, 1.5);
        double expectedScore = 100 * 1.5;
        assertEquals(expectedScore, bidScoreCalculator.calculateScore(bid));
    }

    @Test
    void testCalculateScoreWithZero() {
        Bid bid = new Bid("PublisherB", 0, 2.0);
        assertEquals(0, bidScoreCalculator.calculateScore(bid));
    }

    @Test
    void testCalculateScoreWithNegativeValues() {
        Bid bid = new Bid("PublisherC", -50, 2.0);
        assertEquals(-100, bidScoreCalculator.calculateScore(bid));
    }
}
