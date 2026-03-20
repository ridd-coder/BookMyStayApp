import java.util.*;

class BookingRequest {
    String customerName;
    String roomType;

    public BookingRequest(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single", 1);
        rooms.put("Double", 1);
    }

    public synchronized boolean allocate(String type) {
        if (rooms.get(type) > 0) {
            rooms.put(type, rooms.get(type) - 1);
            return true;
        }
        return false;
    }

    public synchronized void display() {
        System.out.println("Inventory: " + rooms);
    }
}

class BookingQueue {
    private Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest req) {
        queue.add(req);
    }

    public synchronized BookingRequest getRequest() {
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
            BookingRequest req;

            synchronized (queue) {
                req = queue.getRequest();
            }

            if (req == null) break;

            boolean success = inventory.allocate(req.roomType);

            if (success) {
                System.out.println(req.customerName + " booked " + req.roomType);
            } else {
                System.out.println(req.customerName + " failed to book " + req.roomType);
            }
        }
    }
}

public class UC11 {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        queue.addRequest(new BookingRequest("Riddhiman", "Single"));
        queue.addRequest(new BookingRequest("Aman", "Single"));
        queue.addRequest(new BookingRequest("Priya", "Double"));
        queue.addRequest(new BookingRequest("Rahul", "Double"));

        Thread t1 = new BookingProcessor(queue, inventory);
        Thread t2 = new BookingProcessor(queue, inventory);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.display();
    }
}