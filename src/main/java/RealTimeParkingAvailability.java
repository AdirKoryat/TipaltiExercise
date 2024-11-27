import java.util.Optional;

public class RealTimeParkingAvailability {
    public static AvailabilityResponse parkVehicle(Vehicle vehicle, ParkingLot parkingLot) {

        Optional<ParkingSpot> OptParkingSpot = parkingLot.getAvailableParkingSpot(vehicle.getSize());

        if (OptParkingSpot.isPresent()) {
            ParkingSpot parkingSpot = OptParkingSpot.get();
            return new AvailabilityResponse(true, parkingSpot);

        }


        return new AvailabilityResponse(false, null);

    }
}
