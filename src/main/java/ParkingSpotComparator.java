import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ParkingSpotComparator implements Comparator<ParkingSpot> {

    private Map<ParkingSpot, List<Vehicle>> parkingSpotToVehicles;

    public ParkingSpotComparator(Map<ParkingSpot, List<Vehicle>> parkingSpotToVehicles) {
        this.parkingSpotToVehicles = parkingSpotToVehicles;
    }


    @Override
    public int compare(ParkingSpot spot1, ParkingSpot spot2) {
        List<Vehicle> spot1Vehicles = parkingSpotToVehicles.get(spot1);
        List<Vehicle> spot2Vehicles = parkingSpotToVehicles.get(spot2);

        return spot1Vehicles.size() - spot2Vehicles.size();
    }
}
