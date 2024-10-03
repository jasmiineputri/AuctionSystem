import java.util.ArrayList;
import java.util.Scanner;

// Person class
class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// Bid class
class Bid {
    private Person bidder;
    private double amount;

    public Bid(Person bidder, double amount) {
        this.bidder = bidder;
        this.amount = amount;
    }

    public Person getBidder() {
        return bidder;
    }

    public double getAmount() {
        return amount;
    }
}

// Lot class
class Lot {
    private int lotNumber;
    private String description;
    private Bid highestBid;

    public Lot(int lotNumber, String description) {
        this.lotNumber = lotNumber;
        this.description = description;
        this.highestBid = null; // No bids initially
    }

    public int getLotNumber() {
        return lotNumber;
    }

    public String getDescription() {
        return description;
    }

    public void placeBid(Bid bid) {
        if (highestBid == null || bid.getAmount() > highestBid.getAmount()) {
            highestBid = bid;
        } else {
            System.out.println("Bid amount is less than the current highest bid.");
        }
    }

    public Bid getHighestBid() {
        return highestBid;
    }

    public boolean isSold() {
        return highestBid != null;
    }
}

// Auction class
class Auction {
    private ArrayList<Lot> lots;
    private boolean isOpen;

    public Auction() {
        lots = new ArrayList<>();
        isOpen = true;
    }

    public void addLot(String description) {
        int lotNumber = lots.size() + 1;
        lots.add(new Lot(lotNumber, description));
    }

    public void placeBid(int lotNumber, Person person, double amount) {
        if (!isOpen) {
            System.out.println("Auction is closed. Cannot place bids.");
            return;
        }
        Lot lot = getLot(lotNumber);
        if (lot != null) {
            lot.placeBid(new Bid(person, amount));
        } else {
            System.out.println("Lot not found.");
        }
    }

    public Lot getLot(int lotNumber) {
        for (Lot lot : lots) {
            if (lot.getLotNumber() == lotNumber) {
                return lot;
            }
        }
        return null;
    }

    public void closeAuction() {
        isOpen = false;
        System.out.println("Auction is now closed.");
        for (Lot lot : lots) {
            if (lot.isSold()) {
                System.out.println("Lot " + lot.getLotNumber() + " sold to " + lot.getHighestBid().getBidder().getName() + " for $" + lot.getHighestBid().getAmount());
            } else {
                System.out.println("Lot " + lot.getLotNumber() + " was not sold.");
            }
        }
    }
}

public class AuctionSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Auction auction = new Auction();

        System.out.println("Welcome to the Auction System");
        
        // Adding some lots to the auction
        System.out.println("Adding lots...");
        auction.addLot("Antique vase");
        auction.addLot("Vintage painting");
        auction.addLot("Rare book");

        while (true) {
            System.out.println("\nEnter 1 to place a bid, 2 to close the auction, 3 to exit:");
            int choice = scanner.nextInt();
            if (choice == 1) {
                // Place a bid
                System.out.print("Enter your name: ");
                String name = scanner.next();
                Person person = new Person(name);

                System.out.print("Enter lot number: ");
                int lotNumber = scanner.nextInt();

                System.out.print("Enter bid amount: ");
                double amount = scanner.nextDouble();

                auction.placeBid(lotNumber, person, amount);
            } else if (choice == 2) {
                // Close the auction
                auction.closeAuction();
                break;
            } else if (choice == 3) {
                // Exit
                System.out.println("Exiting the auction system.");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }
}
