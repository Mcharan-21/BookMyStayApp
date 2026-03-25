import java.util.*;

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void addReservation(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}

class BookingReportService {

    public void displayAllBookings(List<Reservation> list) {

        System.out.println("Booking History:");

        for (Reservation r : list) {
            System.out.println(
                    r.getReservationId() + " | " +
                            r.getGuestName() + " | " +
                            r.getRoomType()
            );
        }
    }

    public void generateSummary(List<Reservation> list) {

        HashMap<String, Integer> countMap = new HashMap<>();

        for (Reservation r : list) {
            String type = r.getRoomType();
            countMap.put(type, countMap.getOrDefault(type, 0) + 1);
        }

        System.out.println("Booking Summary:");

        for (String type : countMap.keySet()) {
            System.out.println(type + " -> " + countMap.get(type));
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        history.addReservation(new Reservation("RES101", "Charan", "Single Room"));
        history.addReservation(new Reservation("RES102", "Rahul", "Double Room"));
        history.addReservation(new Reservation("RES103", "Anjali", "Suite Room"));
        history.addReservation(new Reservation("RES104", "Kiran", "Single Room"));

        BookingReportService report = new BookingReportService();

        report.displayAllBookings(history.getAllReservations());

        report.generateSummary(history.getAllReservations());
    }
}