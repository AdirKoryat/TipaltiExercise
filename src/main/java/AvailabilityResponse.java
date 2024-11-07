public class AvailabilityResponse {
    private final boolean available;
    private final ParkingSpot parkingSpot;

    public AvailabilityResponse(boolean available, ParkingSpot parkingSpot) {
        this.available = available;
        this.parkingSpot = parkingSpot;
    }

    public boolean isAvailable() {
        return available;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }
}
