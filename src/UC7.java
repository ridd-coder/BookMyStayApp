import java.util.*;

class Reservation {
    private String reservationId;
    private String customerName;

    public Reservation(String reservationId, String customerName) {
        this.reservationId = reservationId;
        this.customerName = customerName;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getCustomerName() {
        return customerName;
    }
}

class AddOnService {
    private String name;
    private double price;

    public AddOnService(String name, double price) {
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

    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    public void showServices(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        double total = 0;

        System.out.println("Services for Reservation " + reservationId + ":");

        for (AddOnService s : services) {
            System.out.println(s.getName() + " - " + s.getPrice());
            total += s.getPrice();
        }

        System.out.println("Total Add-On Cost: " + total);
    }
}

public class UC7 {

    public static void main(String[] args) {

        Reservation r1 = new Reservation("R101", "Alice");

        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService breakfast = new AddOnService("Breakfast", 300);
        AddOnService spa = new AddOnService("Spa", 500);

        AddOnServiceManager manager = new AddOnServiceManager();

        manager.addService(r1.getReservationId(), wifi);
        manager.addService(r1.getReservationId(), breakfast);
        manager.addService(r1.getReservationId(), spa);

        System.out.println("Add-On Service Selection\n");

        manager.showServices(r1.getReservationId());
    }
}