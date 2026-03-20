import java.util.*;

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
   Booking Queue (FIFO)
   ========================= */
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
        System.out.println("Request added: " + r.getCustomerName() + " (" + r.getRoomType() + ")");
    }

    public Queue<Reservation> getQueue() {
        return queue;
    }
}

class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 2);
        availability.put("Double", 1);
        availability.put("Suite", 1);
    }

    public boolean isAvailable(String type) {
        return availability.getOrDefault(type, 0) > 0;
    }

    public void reduceRoom(String type) {
        availability.put(type, availability.get(type) - 1);
    }

    public void showInventory() {
        System.out.println("\nRemaining Rooms:");
        for (String type : availability.keySet()) {
            System.out.println(type + ": " + availability.get(type));
        }
    }
}

/* =========================
   Allocation Service
   ========================= */
class RoomAllocationService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> roomMap = new HashMap<>();

    public void processBookings(BookingRequestQueue queue, RoomInventory inventory) {

        Queue<Reservation> q = queue.getQueue();

        System.out.println("\nProcessing Bookings...\n");

        int idCounter = 1;

        while (!q.isEmpty()) {

            Reservation r = q.poll();
            String type = r.getRoomType();

            if (inventory.isAvailable(type)) {

                String roomId = type.charAt(0) + "-" + idCounter++;

                // Ensure uniqueness
                while (allocatedRoomIds.contains(roomId)) {
                    roomId = type.charAt(0) + "-" + idCounter++;
                }

                allocatedRoomIds.add(roomId);

                roomMap.putIfAbsent(type, new HashSet<>());
                roomMap.get(type).add(roomId);

                inventory.reduceRoom(type);

                System.out.println("Booking Confirmed for " + r.getCustomerName()
                        + " | Room Type: " + type
                        + " | Room ID: " + roomId);

            } else {
                System.out.println("Booking Failed for " + r.getCustomerName()
                        + " | No " + type + " rooms available");
            }
        }
    }
}

public class UC6 {

    public static void main(String[] args) {

        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        System.out.println("Booking System (UC6)\n");

        // Add requests (FIFO)
        queue.addRequest(new Reservation("Alice", "Single"));
        queue.addRequest(new Reservation("Bob", "Double"));
        queue.addRequest(new Reservation("Charlie", "Suite"));
        queue.addRequest(new Reservation("David", "Single")); // may fail

        // Process bookings
        service.processBookings(queue, inventory);

        // Show remaining inventory
        inventory.showInventory();
    }
}
