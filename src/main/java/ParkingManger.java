import java.util.*;

public class ParkingManger {

    private ParkingLot parkingLot;


    public ParkingManger(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }


    public ParkingResponse parkVehicle(Vehicle vehicle) {

        AvailabilityResponse response = RealTimeParkingAvailability.parkVehicle(vehicle, parkingLot);

        if (response.isAvailable()) {
            parkingLot.addVehicle(response.getParkingSpot(), vehicle);


            return new ParkingResponse(true, response.getParkingSpot().getId());
        }

        return new ParkingResponse(false, null);

    }


    public boolean removeVehicle(UUID id) {
        return parkingLot.removeVehicle(id);
    }


}
