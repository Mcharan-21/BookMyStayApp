abstract class Room {

    private String type;
    private int beds;
    private double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2000);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000);
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        Room single = new SingleRoom();
        Room dual = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println(single.getType() + " | Beds: " + single.getBeds() + " | Price: " + single.getPrice() + " | Available: " + singleAvailable);
        System.out.println(dual.getType() + " | Beds: " + dual.getBeds() + " | Price: " + dual.getPrice() + " | Available: " + doubleAvailable);
        System.out.println(suite.getType() + " | Beds: " + suite.getBeds() + " | Price: " + suite.getPrice() + " | Available: " + suiteAvailable);
    }
}