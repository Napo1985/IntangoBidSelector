#Bid Selection Algorithm

## Overview
This project implements a **Bid Selection Algorithm** for a bidding system. The algorithm determines the **winning bid** based on **Bid Amount × Quality Score** and includes **detailed logging** and **unit tests** to verify correctness.

## Assignment Details
### **Task**
Implement a function that:
- Receives a list of `Bid` objects.
- Each `Bid` includes:
    - **Publisher ID** (String)
    - **Bid Amount** (Double)
    - **Quality Score** (Double)
- Returns the **winning publisher ID** based on:
    1. The bid with the highest **(Bid Amount × Quality Score)** wins.
    2. If there's a **tie**, the bid with the **higher bid amount** wins.
    3. If there's a **tie**, also in **higher bid amount** return the first winner
- The function **logs the selection process** for transparency.
- **Unit tests** verify correctness under different scenarios.

---
## **Testing**
### **Unit Tests (`src/test/java/org/example/logic/`)**
- **`BidSelectorTest `** - Tests bid selection logic using **mocked score calculations**.
- **`BidScoreCalculatorTest`** - Tests score calculation correctness.

### **Integration Tests (`src/test/java/org/example/integration/`)**
- **`BidSelectorIntegrationTest`** - Runs **end-to-end tests** to validate bid selection with real components.

---

### **Clone the Repository**
```
git clone https://github.com/Napo1985/IntangoBidSelector.git
```

## **Logging Example**
```
[INFO] Main: Starting Bid Selection Process...
[INFO] BidSelector: ▶ START: selectWinningBid
[INFO] BidSelector: Starting bid selection process | Total bids: 5
[INFO] BidSelector: Processing Bid{Publisher: PublisherA, Amount: 100.00, Quality: 1.50} | Score: 150.00 | New winner
[INFO] BidSelector: Processing Bid{Publisher: PublisherB, Amount: 200.00, Quality: 1.00} | Score: 200.00 | New winner
[INFO] BidSelector: Processing Bid{Publisher: PublisherC, Amount: 150.00, Quality: 1.50} | Score: 225.00 | New winner
[INFO] BidSelector: Processing Bid{Publisher: PublisherE, Amount: 50.00, Quality: 2.00} | Score: 100.00
[INFO] BidSelector: Processing Bid{Publisher: PublisherF, Amount: 100.00, Quality: 1.50} | Score: 150.00
[INFO] BidSelector: Winning bid: PublisherC
[INFO] BidSelector: End of bid selection process
[INFO] BidSelector: ⏹ END: selectWinningBid
[INFO] Main: Winning Publisher: PublisherC
```

