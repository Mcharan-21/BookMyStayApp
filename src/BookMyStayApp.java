import java.util.*;

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrease(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void increase(String type) {
        inventory.put(type, inventory.get(type) + 1);
    }
}

class Reservation {

    private String reservationId;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }
}

class BookingHistory {

    private HashMap<String, Reservation> history;

    public BookingHistory() {
        history = new HashMap<>();
    }

    public void add(Reservation r) {
        history.put(r.getReservationId(), r);
    }

    public Reservation get(String id) {
        return history.get(id);
    }

    public void remove(String id) {
        history.remove(id);
    }
}

class CancellationService {

    private RoomInventory inventory;
    private BookingHistory history;
    private Stack<String> rollbackStack;

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
        rollbackStack = new Stack<>();
    }

    public void cancel(String reservationId) {

        Reservation r = history.get(reservationId);

        if (r == null) {
            System.out.println("Cancellation Failed: Invalid reservation ID");
            return;
        }

        rollbackStack.push(r.getRoomId());

        inventory.increase(r.getRoomType());

        history.remove(reservationId);

        System.out.println("Cancelled Reservation: " + reservationId +
                " | Released Room ID: " + rollbackStack.pop());
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();

        Reservation r1 = new Reservation("RES101", "Single Room", "SI1");
        Reservation r2 = new Reservation("RES102", "Double Room", "DO1");

        inventory.decrease("Single Room");
        inventory.decrease("Double Room");

        history.add(r1);
        history.add(r2);

        CancellationService service = new CancellationService(inventory, history);

        service.cancel("RES101");
        service.cancel("RES999");
    }
}