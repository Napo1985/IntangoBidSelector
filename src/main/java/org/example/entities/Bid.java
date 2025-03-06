package org.example.entities;

public class Bid {
    private final String publisherId;
    private final double bidAmount;
    private final double qualityScore;

    public Bid(String publisherId, double bidAmount, double qualityScore) {
        this.publisherId = publisherId;
        this.bidAmount = bidAmount;
        this.qualityScore = qualityScore;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public double getQualityScore() {
        return qualityScore;
    }

    @Override
    public String toString() {
        return String.format("Bid{Publisher: %s, Amount: %.2f, Quality: %.2f}",
                publisherId, bidAmount, qualityScore);
    }

}
