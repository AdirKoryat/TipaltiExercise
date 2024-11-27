import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ParkingLotUnitTest {

    private ParkingLot parkingLot;
    private ParkingSpot mockSmallSpot;
    private ParkingSpot mockMediumSpot;
    private ParkingSpot mockLargeSpot;
    private Vehicle mockSmallVehicle;
    private Vehicle mockMediumVehicle;
    private Vehicle mockLargeVehicle;

    @BeforeEach
    void setup() {
        mockSmallSpot = mock(ParkingSpot.class);
        mockMediumSpot = mock(ParkingSpot.class);
        mockLargeSpot = mock(ParkingSpot.class);

        mockSmallVehicle = mock(Vehicle.class);
        mockMediumVehicle = mock(Vehicle.class);
        mockLargeVehicle = mock(Vehicle.class);

        when(mockSmallSpot.getSize()).thenReturn(Size.SMALL);
        when(mockMediumSpot.getSize()).thenReturn(Size.MEDIUM);
        when(mockLargeSpot.getSize()).thenReturn(Size.LARGE);

        when(mockSmallVehicle.getSize()).thenReturn(Size.SMALL);
        when(mockMediumVehicle.getSize()).thenReturn(Size.MEDIUM);
        when(mockLargeVehicle.getSize()).thenReturn(Size.LARGE);

        Map<ParkingSpot, List<Vehicle>> initialSpots = new HashMap<>();
        initialSpots.put(mockSmallSpot, new ArrayList<>());
        initialSpots.put(mockMediumSpot, new ArrayList<>());
        initialSpots.put(mockLargeSpot, new ArrayList<>());

        parkingLot = new ParkingLot(initialSpots);
    }

    @Test
    void testAddVehicleToSmallSpot() {

        Map<Size, Queue<ParkingSpot>> sizeToParkingSpotsTrackerBefore = parkingLot.getSizeToParkingSpotsTracker();
        parkingLot.addVehicle(mockSmallSpot, mockSmallVehicle);
        Map<Size, Queue<ParkingSpot>> sizeToParkingSpotsTrackerAfter = parkingLot.getSizeToParkingSpotsTracker();
        Map<ParkingSpot, List<Vehicle>> parkingSpotsToVehicles = parkingLot.getParkingSpotsToVehicles();
        assertTrue(parkingSpotsToVehicles.get(mockSmallSpot).contains(mockSmallVehicle));
        assertNotSame(sizeToParkingSpotsTrackerBefore, sizeToParkingSpotsTrackerAfter);
        assertEquals(1, parkingSpotsToVehicles.get(mockSmallSpot).size());
    }

    @Test
    void testRemoveVehicleFromSpot() {
        parkingLot.addVehicle(mockMediumSpot, mockMediumVehicle);
        UUID testId = UUID.randomUUID();
        when(mockMediumVehicle.getId()).thenReturn(testId);

        boolean removed = parkingLot.removeVehicle(mockMediumVehicle.getId());
        assertTrue(removed);
        Map<ParkingSpot, List<Vehicle>> parkingSpotsToVehicles = parkingLot.getParkingSpotsToVehicles();
        assertEquals(0, parkingSpotsToVehicles.get(mockMediumSpot).size());
    }

    @Test
    void testGetAvailableParkingSpot() {
        Optional<ParkingSpot> availableSpot = parkingLot.getAvailableParkingSpot(Size.SMALL);
        assertTrue(availableSpot.isPresent());
        Set<ParkingSpot> allParkingSpots = new HashSet<>();
        allParkingSpots.add(mockSmallSpot);
        allParkingSpots.add(mockMediumSpot);
        allParkingSpots.add(mockLargeSpot);
        assertTrue(allParkingSpots.contains(availableSpot.get()));
    }

    @Test
    void testUpdateTracker() {
        // We have one small spot so when we park a car there the small spot should not be available anymore
        parkingLot.addVehicle(mockSmallSpot, mockSmallVehicle);
        Map<Size, Queue<ParkingSpot>> sizeToParkingSpotsTracker = parkingLot.getSizeToParkingSpotsTracker();
        assertFalse(sizeToParkingSpotsTracker.get(Size.SMALL).contains(mockSmallSpot));
    }
}
