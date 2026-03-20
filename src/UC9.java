import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    public void validateRoomType(String type) throws InvalidBookingException {
        if (!rooms.containsKey(type)) {
            throw new InvalidBookingException("Invalid room type: " + type);
        }
    }

    public void validateAvailability(String type) throws InvalidBookingException {
        if (rooms.get(type) <= 0) {
            throw new InvalidBookingException("No rooms available for: " + type);
        }
    }

    public void bookRoom(String type) {
        rooms.put(type, rooms.get(type) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Availability:");
        for (String type : rooms.keySet()) {
            System.out.println(type + " -> " + rooms.get(type));
        }
    }
}

class BookingService {
    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void createBooking(String customerName, String roomType) {
        try {
            if (customerName == null || customerName.isEmpty()) {
                throw new InvalidBookingException("Customer name cannot be empty");
            }

            inventory.validateRoomType(roomType);
            inventory.validateAvailability(roomType);

            inventory.bookRoom(roomType);

            System.out.println("Booking successful for " + customerName + " (" + roomType + ")");
        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }
}

public class UC9{
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        service.createBooking("Riddhiman", "Single");
        service.createBooking("", "Double");
        service.createBooking("Aman", "Luxury");
        service.createBooking("Priya", "Suite");
        service.createBooking("Rahul", "Suite");

        inventory.displayInventory();
    }
}