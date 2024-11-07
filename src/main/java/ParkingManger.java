import java.util.*;

public class ParkingManger {

    private ParkingLot parkingLot;


    public ParkingManger(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }


    public ParkingResponse parkVehicle(Vehicle vehicle) {

        AvailabilityResponse response = RealTimeParkingAvailability.parkVehicle(vehicle, parkingLot);

        if (response.isAvailable()) {
            ParkingSpot parkingSpot = response.getParkingSpot();
            List<Vehicle> vehicles =  parkingLot.getParkingSpotsToVehicles().get(parkingSpot);
            vehicles.add(vehicle);
            parkingLot
                    .getParkingSpotsToAvailableParking()
                    .put(parkingSpot, RealTimeParkingAvailability.updateAvailabilityParkingSpot(parkingSpot.getSize(), vehicles));


            return new ParkingResponse(true, response.getParkingSpot().getId());
        }

        return new ParkingResponse(false, null);

    }


    public boolean removeVehicle(UUID id) {

        Optional<Map.Entry<ParkingSpot, List<Vehicle>>> entryParkingSpotToVehicles =
                parkingLot
                        .getParkingSpotsToVehicles()
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
            parkingLot.getParkingSpotsToVehicles().put(parkingSpot, vehicles);
            parkingLot
                    .getParkingSpotsToAvailableParking()
                    .put(parkingSpot, RealTimeParkingAvailability.updateAvailabilityParkingSpot(parkingSpot.getSize(), vehicles));
            return true;
        }
        return false;

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
