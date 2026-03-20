import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    private String id;
    private String customerName;
    private String roomType;

    public Reservation(String id, String customerName, String roomType) {
        this.id = id;
        this.customerName = customerName;
        this.roomType = roomType;
    }

    public String getRoomType() {
        return roomType;
    }

    public String toString() {
        return id + " - " + customerName + " (" + roomType + ")";
    }
}

class RoomInventory implements Serializable {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single", 2);
        rooms.put("Double", 2);
    }

    public void bookRoom(String type) {
        rooms.put(type, rooms.get(type) - 1);
    }

    public void display() {
        System.out.println("Inventory: " + rooms);
    }

    public Map<String, Integer> getRooms() {
        return rooms;
    }

    public void setRooms(Map<String, Integer> rooms) {
        this.rooms = rooms;
    }
}

class BookingSystemState implements Serializable {
    List<Reservation> reservations;
    Map<String, Integer> inventory;

    public BookingSystemState(List<Reservation> reservations, Map<String, Integer> inventory) {
        this.reservations = reservations;
        this.inventory = inventory;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "booking_data.ser";

    public void save(List<Reservation> reservations, RoomInventory inventory) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            BookingSystemState state = new BookingSystemState(reservations, inventory.getRooms());
            oos.writeObject(state);
            System.out.println("Data saved successfully");
        } catch (IOException e) {
            System.out.println("Error saving data");
        }
    }

    public BookingSystemState load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (BookingSystemState) ois.readObject();
        } catch (Exception e) {
            System.out.println("No previous data found, starting fresh");
            return null;
        }
    }
}

public class UC12 {
    public static void main(String[] args) {
        PersistenceService service = new PersistenceService();
        RoomInventory inventory = new RoomInventory();
        List<Reservation> reservations = new ArrayList<>();

        BookingSystemState state = service.load();

        if (state != null) {
            reservations = state.reservations;
            inventory.setRooms(state.inventory);
            System.out.println("System recovered successfully");
        }

        reservations.add(new Reservation("B1", "Riddhiman", "Single"));
        inventory.bookRoom("Single");

        reservations.add(new Reservation("B2", "Aman", "Double"));
        inventory.bookRoom("Double");

        System.out.println("\nCurrent Bookings:");
        for (Reservation r : reservations) {
            System.out.println(r);
        }

        inventory.display();

        service.save(reservations, inventory);
    }
}