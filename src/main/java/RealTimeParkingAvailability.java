import java.util.List;

public class RealTimeParkingAvailability {
    public static AvailabilityResponse parkVehicle(Vehicle vehicle, ParkingLot parkingLot) {

                for (ParkingSpot parkingSpot: parkingLot.getParkingSpots().keySet()) {
                    if (canAccommodate(parkingLot.getParkingSpots().get(parkingSpot), vehicle.getSize(), parkingSpot.getSize())) {
                        return new AvailabilityResponse(true, parkingSpot);
                    }

                }
                return new AvailabilityResponse(false, null);

    }

    public static boolean canAccommodate(List<Vehicle> vehicles, Size vehicleSize, Size spotSize) {
        switch (vehicleSize) {
            case SMALL -> {
                if (spotSize.equals(Size.SMALL) && vehicles.isEmpty()) {
                    return true;
                } else if (spotSize.equals(Size.MEDIUM)
                           && vehicles.size() < 2) {
                    return vehicles.stream().noneMatch(v -> v.getSize().equals(Size.MEDIUM));

                } else if (spotSize.equals(Size.LARGE)
                           && vehicles.size() < 3) {
                    if (vehicles.stream().allMatch(v -> v.getSize().equals(Size.SMALL))) {
                        return true;
                    } else return vehicles.stream().anyMatch(v -> v.getSize().equals(Size.MEDIUM))
                            && vehicles.size() < 2;
                }
                return false;
            }

            case MEDIUM -> {
                if (spotSize.equals(Size.MEDIUM) && vehicles.isEmpty()) {
                    return true;
                } else if (spotSize.equals(Size.LARGE)
                           && vehicles.size() < 2) {
                    return vehicles
                            .stream()
                            .noneMatch(v -> v.getSize().equals(Size.MEDIUM) || v.getSize().equals(Size.LARGE));
                }
                return false;
            }

            case LARGE -> {
                return spotSize.equals(Size.LARGE) && vehicles.isEmpty();
            }

            default -> {
                return false;
            }
        }
    }
}
