import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ParkingMangerUnitTest {

    private ParkingManger parkingManger;
    private ParkingLot parkingLot;

    @BeforeEach
    public void setup() {
        parkingLot = Mockito.mock(ParkingLot.class);
        parkingManger = new ParkingManger(parkingLot);

    }

    @Test
    public void testParkSmallVehicleInSmallSpotIsSuccess() {
        Vehicle motorcycle = new Motorcycle();
        ParkingSpot parkingSpot = new ParkingSpot(Size.SMALL);
        when(parkingLot.getAvailableParkingSpot(motorcycle.getSize())).thenReturn(Optional.of(parkingSpot));
        ParkingResponse parkingResponse = parkingManger.parkVehicle(motorcycle);

        assertTrue(parkingResponse.isSuccess());
    }


    @Test
    public void testParkSmallVehicleInSmallSpotIsFailure() {
        Vehicle motorcycle = new Motorcycle();
        when(parkingLot.getAvailableParkingSpot(motorcycle.getSize())).thenReturn(Optional.empty());
        ParkingResponse parkingResponse = parkingManger.parkVehicle(motorcycle);

        assertFalse(parkingResponse.isSuccess());
    }


    @Test
    public void testRemoveSmallVehicleSuccess() {
        Vehicle motorcycle = new Motorcycle();
        when(parkingLot.removeVehicle(motorcycle.getId())).thenReturn(true);
        boolean isRemoved = parkingManger.removeVehicle(motorcycle.getId());
        assertTrue(isRemoved);
    }

    @Test
    public void testRemoveSmallVehicleFailure() {
        Vehicle motorcycle = new Motorcycle();
        when(parkingLot.removeVehicle(motorcycle.getId())).thenReturn(false);
        boolean isRemoved = parkingManger.removeVehicle(motorcycle.getId());
        assertFalse(isRemoved);
    }
}
