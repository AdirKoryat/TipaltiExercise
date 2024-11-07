import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RealTimeParkingAvailability {
    public static AvailabilityResponse parkVehicle(Vehicle vehicle, ParkingLot parkingLot) {

                for (ParkingSpot parkingSpot: parkingLot.getParkingSpotsToVehicles().keySet()) {
                    if (canAccommodate(parkingLot.getParkingSpotsToVehicles().get(parkingSpot), vehicle.getSize(), parkingSpot.getSize())) {
                        return new AvailabilityResponse(true, parkingSpot);
                    }

                }
                return new AvailabilityResponse(false, null);

    }

    public static void updateAvailabilityTracker(ParkingLot parkingLot) {
        for (Map.Entry<ParkingSpot, List<Vehicle>> entry: parkingLot.getParkingSpotsToVehicles().entrySet()) {
            ParkingSpot parkingSpot = entry.getKey();
            List<Vehicle> vehicles = entry.getValue();
            parkingLot
                    .getParkingSpotsToAvailableParking()
                    .put(parkingSpot, updateAvailabilityParkingSpot(parkingSpot.getSize(), vehicles));
        }


    }

    public static Map<Size, Integer> updateAvailabilityParkingSpot(Size spotSize, List<Vehicle> vehicles) {
        Map<Size, Integer> result = new HashMap<>();
        result.put(Size.SMALL, 0);
        result.put(Size.MEDIUM, 0);
        result.put(Size.LARGE, 0);
        switch (spotSize) {
            case SMALL -> {
                if (vehicles.isEmpty()) {
                    result.put(Size.SMALL, 1);

                }
                return result;
            }

            case MEDIUM -> {
                if (vehicles.isEmpty()) {
                    result.put(Size.SMALL, 2);
                    result.put(Size.MEDIUM, 1);
                }
                else if (vehicles.size() < 2 && vehicles.get(0).getSize().equals(Size.SMALL)) {
                    result.put(Size.SMALL, 1);
                }
                else {
                    return result;
                }
            }
            case LARGE -> {
                if (vehicles.isEmpty()) {
                    result.put(Size.SMALL, 3);
                    result.put(Size.MEDIUM, 1);
                    result.put(Size.LARGE, 1);
                }
                else if (vehicles.size() == 1) {
                    if (vehicles.get(0).getSize().equals(Size.SMALL)) {
                        result.put(Size.SMALL, 2);
                        result.put(Size.MEDIUM, 1);
                    } else if(vehicles.get(0).getSize().equals(Size.MEDIUM)) {
                        result.put(Size.SMALL, 1);
                    }
                } else if (vehicles.size() == 2) {
                    if (vehicles.stream().allMatch(vehicle -> vehicle.getSize().equals(Size.SMALL))) {
                        result.put(Size.SMALL, 1);
                    }
                }
                return result;
            }
        }
        return result;
    }

    private static boolean canAccommodate(List<Vehicle> vehicles, Size vehicleSize, Size spotSize) {
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
