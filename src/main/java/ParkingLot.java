import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ParkingLot {

    private UUID id;

    private Map<ParkingSpot, List<Vehicle>> parkingSpotsToVehicles;
    // TODO: convert to Map<Size, PriorityQueue<ParkingSpot>>
    private Map<ParkingSpot, Map<Size, Integer>> parkingSpotsToAvailableParking;


    public ParkingLot(Map<ParkingSpot, List<Vehicle>> parkingSpots) {
        this.id = UUID.randomUUID();
        this.parkingSpotsToVehicles = parkingSpots;
    }

    public Map<ParkingSpot, List<Vehicle>> getParkingSpotsToVehicles() {
        return parkingSpotsToVehicles;
    }

    public Map<ParkingSpot, Map<Size, Integer>> getParkingSpotsToAvailableParking() {
        return parkingSpotsToAvailableParking;
    }

    // TODO: function to add vehicle to parkingSpot
}
