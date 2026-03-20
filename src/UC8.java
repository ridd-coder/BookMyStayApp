import java.util.*;

class Reservation {
    private String customerName;
    private String roomType;
    private int nights;
    private double totalPrice;

    public Reservation(String customerName, String roomType, int nights, double totalPrice) {
        this.customerName = customerName;
        this.roomType = roomType;
        this.nights = nights;
        this.totalPrice = totalPrice;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String toString() {
        return "Customer: " + customerName +
                ", Room: " + roomType +
                ", Nights: " + nights +
                ", Total: ₹" + totalPrice;
    }
}

class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void addReservation(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}

class BookingReportService {
    public void generateReport(List<Reservation> reservations) {
        System.out.println("\n--- Booking Report ---");

        double totalRevenue = 0;
        int totalBookings = reservations.size();

        for (Reservation r : reservations) {
            System.out.println(r);
            totalRevenue += r.getTotalPrice();
        }

        System.out.println("\nTotal Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }
}

public class UC8 {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        Reservation r1 = new Reservation("Riddhiman", "Single", 2, 2000);
        Reservation r2 = new Reservation("Aman", "Double", 3, 4500);
        Reservation r3 = new Reservation("Priya", "Suite", 1, 3000);

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        reportService.generateReport(history.getAllReservations());
    }
}