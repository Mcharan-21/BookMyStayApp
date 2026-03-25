import java.util.*;

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
    }

    public synchronized boolean bookRoom(String type) {

        int count = inventory.getOrDefault(type, 0);

        if (count > 0) {
            inventory.put(type, count - 1);
            return true;
        }

        return false;
    }
}

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingQueue {

    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    public synchronized void add(Reservation r) {
        queue.add(r);
    }

    public synchronized Reservation get() {
        return queue.poll();
    }
}

class BookingProcessor extends Thread {

    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {

        while (true) {

            Reservation r;

            synchronized (queue) {
                r = queue.get();
            }

            if (r == null) break;

            boolean success;

            synchronized (inventory) {
                success = inventory.bookRoom(r.getRoomType());
            }

            if (success) {
                System.out.println(Thread.currentThread().getName() +
                        " confirmed booking for " + r.getGuestName());
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " failed booking for " + r.getGuestName());
            }
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        queue.add(new Reservation("Charan", "Single Room"));
        queue.add(new Reservation("Rahul", "Single Room"));
        queue.add(new Reservation("Anjali", "Single Room"));
        queue.add(new Reservation("Kiran", "Single Room"));

        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);

        t1.setName("Thread-1");
        t2.setName("Thread-2");

        t1.start();
        t2.start();
    }
}