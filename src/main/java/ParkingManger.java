import java.util.*;

public class ParkingManger {

    private ParkingLot parkingLot;


    public ParkingManger(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }


    public ParkingResponse parkVehicle(Vehicle vehicle) {

        AvailabilityResponse response = RealTimeParkingAvailability.parkVehicle(vehicle, parkingLot);

        if (response.isAvailable()) {
            parkingLot.getParkingSpots().get(response.getParkingSpot()).add(vehicle);

            return new ParkingResponse(true, response.getParkingSpot().getId());
        }

        return new ParkingResponse(false, null);

    }


    public boolean removeVehicle(UUID id) {
        return parkingLot
                .getParkingSpots()
                .entrySet()
                .stream()
                .anyMatch(parkingSpotListEntry ->
                        parkingSpotListEntry
                                .getValue()
                                .removeIf(vehicle -> vehicle.getId().equals(id)));

    }
//        Optional<ParkingSpot> parkingSpotOpt = parkingLot.getParkingSpots().stream()
//                .filter(spot -> spot.getVehicles().stream().anyMatch(vehicle -> vehicle.getId().equals(id)))
//                .findFirst();
//
//        if (parkingSpotOpt.isPresent()) {
//            ParkingSpot parkingSpot = parkingSpotOpt.get();
//
//            Optional<Vehicle> vehicleToRemoveOpt = parkingSpot
//                    .getVehicles()
//                    .stream()
//                    .filter(vehicle -> vehicle.getId().equals(id))
//                    .findFirst();
//
//            if (vehicleToRemoveOpt.isPresent()) {
//                Vehicle vehicleToRemove = vehicleToRemoveOpt.get();
//                return parkingSpotToVehicle
//                        .get(parkingSpot)
//                        .remove(vehicleToRemove);
//            }
//
//        }
//        return false;
//    }


}
