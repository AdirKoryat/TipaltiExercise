import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ParkingLot {

    private UUID id;

    private Map<ParkingSpot, List<Vehicle>> parkingSpots;


    public ParkingLot(Map<ParkingSpot, List<Vehicle>> parkingSpots) {
        this.id = UUID.randomUUID();
        this.parkingSpots = parkingSpots;
    }

    public Map<ParkingSpot, List<Vehicle>> getParkingSpots() {
        return parkingSpots;
    }
}
