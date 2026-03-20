import java.util.*;

/* =========================
   Room Class
   ========================= */
class Room {
    private String type;
    private int beds;
    private int size;
    private double price;

    public Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getType() {
        return type;
    }
}

/* =========================
   Reservation Class
   ========================= */
class Reservation {
    private String customerName;
    private String roomType;

    public Reservation(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/* =========================
   Booking Request Queue (FIFO)
   ========================= */
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request
    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Request added for " + reservation.getCustomerName()
                + " (" + reservation.getRoomType() + ")");
    }

    // View all requests
    public void showRequests() {
        System.out.println("\nCurrent Booking Queue:");

        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : queue) {
            System.out.println(r.getCustomerName() + " -> " + r.getRoomType());
        }
    }
}

/* =========================
   MAIN CLASS (UC5)
   ========================= */
public class UC5{

    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        System.out.println("Booking Request Queue (FIFO)\n");

        // Simulating requests
        bookingQueue.addRequest(new Reservation("Alice", "Single"));
        bookingQueue.addRequest(new Reservation("Bob", "Double"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite"));
        bookingQueue.addRequest(new Reservation("David", "Single"));

        // Display queue
        bookingQueue.showRequests();
    }
}