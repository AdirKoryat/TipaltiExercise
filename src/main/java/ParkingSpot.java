
import java.util.UUID;

public class ParkingSpot {
    private final UUID id;
    private final Size size;


    public ParkingSpot(Size size) {
        this.id = UUID.randomUUID();
        this.size = size;
    }

    public UUID getId() {
        return id;
    }

    public Size getSize() {
        return size;
    }


    //    public boolean canAccommodate(T vehicle) {
//        switch (size) {
//            case SMALL -> {
//                return vehicle.getSize() == Size.SMALL && vehicles.isEmpty();
//            }
//            case MEDIUM -> {
//                if (vehicle.getSize() == Size.SMALL) {
//                    return vehicles.size() < 2;
//                } else if (vehicle.getSize() == Size.MEDIUM) {
//                    return vehicles.isEmpty();
//                }
//            }
//            case LARGE -> {
//                if (vehicle.getSize() == Size.SMALL) {
//                    return vehicles.size() < 3;
//                } else if (vehicle.getSize() == Size.MEDIUM) {
//                    return vehicles.isEmpty() || (vehicles.size() == 1 && vehicles.get(0).getSize() == Size.SMALL);
//                } else if (vehicle.getSize() == Size.LARGE) {
//                    return vehicles.isEmpty();
//                }
//            }
//        }
//        return false;
//    }
//
//    public boolean parkVehicle(T vehicle) {
//        if (canAccommodate(vehicle)) {
//            vehicles.add(vehicle);
//            return true;
//        }
//        return false;
//    }
//
//    public boolean removeVehicle(UUID vehicleId) {
//        return vehicles.removeIf(vehicle -> vehicle.getId().equals(vehicleId));
//    }
}
