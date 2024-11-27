import java.util.*;

public class ParkingLot {

    private final UUID id;

    private Map<ParkingSpot, List<Vehicle>> parkingSpotsToVehicles;
    private Map<Size, Queue<ParkingSpot>> sizeToParkingSpotsTracker;


    public ParkingLot(Map<ParkingSpot, List<Vehicle>> parkingSpots) {
        this.id = UUID.randomUUID();
        this.parkingSpotsToVehicles = parkingSpots;
        this.sizeToParkingSpotsTracker = initTracker();
    }

    public Map<ParkingSpot, List<Vehicle>> getParkingSpotsToVehicles() {
        return Collections.unmodifiableMap(parkingSpotsToVehicles);
    }

    public Map<Size, Queue<ParkingSpot>> getSizeToParkingSpotsTracker() {
        return Collections.unmodifiableMap(sizeToParkingSpotsTracker);
    }




    // TODO: function to add vehicle to parkingSpot

    public void addVehicle(ParkingSpot parkingSpot, Vehicle vehicle) {
        parkingSpotsToVehicles.get(parkingSpot).add(vehicle);
        updateTracker(parkingSpot);
    }

    public boolean removeVehicle(UUID id) {
        Optional<Map.Entry<ParkingSpot, List<Vehicle>>> entryParkingSpotToVehicles =
                parkingSpotsToVehicles
                        .entrySet()
                        .stream()
                        .filter(entry -> entry
                                .getValue()
                                .stream()
                                .anyMatch(vehicle -> vehicle.getId().equals(id)))
                        .findFirst();
        if (entryParkingSpotToVehicles.isPresent()) {
            ParkingSpot parkingSpot = entryParkingSpotToVehicles.get().getKey();
            List<Vehicle> vehicles = entryParkingSpotToVehicles.get().getValue();
            vehicles.removeIf(vehicle -> vehicle.getId().equals(id));
            updateTracker(parkingSpot);
            return true;
        }
        return false;
    }

    public Optional<ParkingSpot> getAvailableParkingSpot(Size vehicleSize) {

        return Optional.ofNullable(sizeToParkingSpotsTracker.get(vehicleSize).poll());

    }

    private void updateTracker(ParkingSpot parkingSpot) {
        List<Vehicle> vehicles = parkingSpotsToVehicles.get(parkingSpot);

        for (Size vehicleSize : Size.values()) {
            sizeToParkingSpotsTracker.get(vehicleSize).remove(parkingSpot);
            if (canAccommodate(vehicles, vehicleSize, parkingSpot.getSize())) {
                sizeToParkingSpotsTracker.get(vehicleSize).add(parkingSpot);
            }
        }
    }


    private Map<Size, Queue<ParkingSpot>> initTracker() {
        Map<Size, Queue<ParkingSpot>> tracker = new HashMap<>();
        for (Size vehicleSize : Size.values()) {
            PriorityQueue<ParkingSpot> parkingSpotQueue = new PriorityQueue<>(new ParkingSpotComparator(parkingSpotsToVehicles));
            tracker.put(vehicleSize, parkingSpotQueue);
        }
        for (Map.Entry<ParkingSpot, List<Vehicle>> entry: parkingSpotsToVehicles.entrySet()) {
            ParkingSpot parkingSpot = entry.getKey();
            List<Vehicle> vehicles = entry.getValue();

            for (Size vehicleSize : Size.values()) {
                if (canAccommodate(vehicles, vehicleSize, parkingSpot.getSize())) {
                    tracker.get(vehicleSize).add(parkingSpot);
                }
            }
        }
        return tracker;
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
