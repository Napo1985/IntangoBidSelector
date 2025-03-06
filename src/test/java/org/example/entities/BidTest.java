package org.example.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BidTest {
    @Test
    void testBidCreation() {
        Bid bid = new Bid("PublisherA", 100, 1.5);

        assertNotNull(bid);
        assertEquals("PublisherA", bid.getPublisherId());
        assertEquals(100, bid.getBidAmount());
        assertEquals(1.5, bid.getQualityScore());
    }
}
