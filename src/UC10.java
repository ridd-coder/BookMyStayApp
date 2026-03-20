import java.util.*;

class Reservation {
    private String id;
    private String customerName;
    private String roomType;

    public Reservation(String id, String customerName, String roomType) {
        this.id = id;
        this.customerName = customerName;
        this.roomType = roomType;
    }

    public String getId() {
        return id;
    }

    public String getRoomType() {
        return roomType;
    }

    public String toString() {
        return id + " - " + customerName + " (" + roomType + ")";
    }
}

class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    public void bookRoom(String type) {
        rooms.put(type, rooms.get(type) - 1);
    }

    public void releaseRoom(String type) {
        rooms.put(type, rooms.get(type) + 1);
    }

    public void display() {
        System.out.println("\nInventory:");
        for (String type : rooms.keySet()) {
            System.out.println(type + " -> " + rooms.get(type));
        }
    }
}

class BookingHistory {
    private Map<String, Reservation> bookings = new HashMap<>();

    public void add(Reservation r) {
        bookings.put(r.getId(), r);
    }

    public Reservation get(String id) {
        return bookings.get(id);
    }

    public void remove(String id) {
        bookings.remove(id);
    }

    public void display() {
        System.out.println("\nActive Bookings:");
        for (Reservation r : bookings.values()) {
            System.out.println(r);
        }
    }
}

class CancellationService {
    private RoomInventory inventory;
    private BookingHistory history;
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }

    public void cancel(String bookingId) {
        Reservation r = history.get(bookingId);

        if (r == null) {
            System.out.println("Cancellation failed: Booking not found");
            return;
        }

        rollbackStack.push(r.getRoomType());

        inventory.releaseRoom(r.getRoomType());
        history.remove(bookingId);

        System.out.println("Cancelled booking: " + bookingId);
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack: " + rollbackStack);
    }
}

public class UC10 {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        CancellationService service = new CancellationService(inventory, history);

        Reservation r1 = new Reservation("B1", "Riddhiman", "Single");
        Reservation r2 = new Reservation("B2", "Aman", "Double");

        history.add(r1);
        history.add(r2);

        inventory.bookRoom("Single");
        inventory.bookRoom("Double");

        history.display();
        inventory.display();

        service.cancel("B1");
        service.cancel("B3");

        history.display();
        inventory.display();

        service.showRollbackStack();
    }
}