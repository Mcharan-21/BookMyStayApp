import java.io.*;
import java.util.*;

class RoomInventory implements Serializable {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(HashMap<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public void display() {
        System.out.println("Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " -> " + inventory.get(key));
        }
    }
}

class Reservation implements Serializable {

    private String reservationId;
    private String guestName;

    public Reservation(String reservationId, String guestName) {
        this.reservationId = reservationId;
        this.guestName = guestName;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }
}

class BookingHistory implements Serializable {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void add(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAll() {
        return history;
    }

    public void display() {
        System.out.println("Booking History:");
        for (Reservation r : history) {
            System.out.println(r.getReservationId() + " | " + r.getGuestName());
        }
    }
}

class PersistenceService {

    private static final String FILE_NAME = "data.ser";

    public void save(RoomInventory inventory, BookingHistory history) {

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            out.writeObject(inventory);
            out.writeObject(history);
            out.close();
            System.out.println("Data saved successfully");
        } catch (Exception e) {
            System.out.println("Error saving data");
        }
    }

    public Object[] load() {

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME));
            RoomInventory inventory = (RoomInventory) in.readObject();
            BookingHistory history = (BookingHistory) in.readObject();
            in.close();
            System.out.println("Data loaded successfully");
            return new Object[]{inventory, history};
        } catch (Exception e) {
            System.out.println("No previous data found, starting fresh");
            return null;
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        PersistenceService service = new PersistenceService();

        Object[] data = service.load();

        RoomInventory inventory;
        BookingHistory history;

        if (data != null) {
            inventory = (RoomInventory) data[0];
            history = (BookingHistory) data[1];
        } else {
            inventory = new RoomInventory();
            history = new BookingHistory();
        }

        history.add(new Reservation("RES101", "Charan"));
        history.add(new Reservation("RES102", "Rahul"));

        inventory.display();
        history.display();

        service.save(inventory, history);
    }
}