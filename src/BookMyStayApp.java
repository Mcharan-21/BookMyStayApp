import java.util.*;

class Service {

    private String name;
    private double price;

    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class AddOnServiceManager {

    private HashMap<String, List<Service>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    public void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    public double getTotalCost(String reservationId) {
        double total = 0;
        List<Service> services = serviceMap.getOrDefault(reservationId, new ArrayList<>());

        for (Service s : services) {
            total += s.getPrice();
        }

        return total;
    }

    public void displayServices(String reservationId) {
        List<Service> services = serviceMap.getOrDefault(reservationId, new ArrayList<>());

        System.out.println("Services for Reservation " + reservationId + ":");

        for (Service s : services) {
            System.out.println(s.getName() + " - " + s.getPrice());
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        String reservation1 = "RES101";
        String reservation2 = "RES102";

        Service wifi = new Service("WiFi", 200);
        Service breakfast = new Service("Breakfast", 300);
        Service spa = new Service("Spa", 1000);

        AddOnServiceManager manager = new AddOnServiceManager();

        manager.addService(reservation1, wifi);
        manager.addService(reservation1, breakfast);

        manager.addService(reservation2, spa);

        manager.displayServices(reservation1);
        System.out.println("Total Cost: " + manager.getTotalCost(reservation1));

        manager.displayServices(reservation2);
        System.out.println("Total Cost: " + manager.getTotalCost(reservation2));
    }
}