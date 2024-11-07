import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ParkingMangerTest {

    private ParkingManger parkingManger;
    private ParkingLot parkingLot;

    @BeforeEach
    public void setup() {
//        Map<ParkingSpot, List<Vehicle>> parkingSpots = new HashMap<>();
//        parkingSpots.put(new ParkingSpot(Size.SMALL), new ArrayList<>());
//        parkingSpots.put(new ParkingSpot(Size.SMALL), new ArrayList<>());
//        parkingSpots.put(new ParkingSpot(Size.MEDIUM), new ArrayList<>());
//        parkingSpots.put(new ParkingSpot(Size.MEDIUM), new ArrayList<>());
//        parkingSpots.put(new ParkingSpot(Size.LARGE), new ArrayList<>());
//        parkingSpots.put(new ParkingSpot(Size.LARGE), new ArrayList<>());
        parkingLot = Mockito.mock(ParkingLot.class);
        parkingManger = new ParkingManger(parkingLot);

    }

    @Test
    public void testParkSmallVehicleInSmallSpotIsSuccess() {
        Vehicle motorcycle = new Motorcycle();
        Map<ParkingSpot, List<Vehicle>> parkingSpots = new HashMap<>();
        ParkingSpot parkingSpot = new ParkingSpot(Size.SMALL);
        parkingSpots.put(parkingSpot, new ArrayList<>());
        when(parkingLot.getParkingSpotsToVehicles()).thenReturn(parkingSpots);
        ParkingResponse parkingResponse = parkingManger.parkVehicle(motorcycle);

        assertTrue(parkingResponse.isSuccess());
        assertEquals(1, parkingLot.getParkingSpotsToVehicles().get(parkingSpot).size());
    }


    @Test
    public void testParkSmallVehicleInSmallSpotIsFailure() {
        Vehicle motorcycle = new Motorcycle();
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(motorcycle);
        ParkingSpot parkingSpot = new ParkingSpot(Size.SMALL);
        Map<ParkingSpot, List<Vehicle>> parkingSpots = new HashMap<>();
        parkingSpots.put(parkingSpot, vehicles);
        when(parkingLot.getParkingSpotsToVehicles()).thenReturn(parkingSpots);
        ParkingResponse parkingResponse = parkingManger.parkVehicle(motorcycle);

        assertFalse(parkingResponse.isSuccess());
    }


    @Test
    public void testRemoveSmallVehicle() {
        Vehicle motorcycle = new Motorcycle();
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(motorcycle);
        ParkingSpot parkingSpot = new ParkingSpot(Size.SMALL);
        Map<ParkingSpot, List<Vehicle>> parkingSpots = new HashMap<>();
        parkingSpots.put(parkingSpot, vehicles);
        when(parkingLot.getParkingSpotsToVehicles()).thenReturn(parkingSpots);
        boolean isRemoved = parkingManger.removeVehicle(motorcycle.getId());
        assertTrue(isRemoved);
    }
}
