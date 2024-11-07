import java.util.UUID;

public class ParkingResponse {
    private boolean success;
    private UUID parkingSpotId;

    public ParkingResponse(boolean success, UUID parkingSpotId) {
        this.success = success;
        this.parkingSpotId = parkingSpotId;
    }

    public boolean isSuccess() {
        return success;
    }

    public UUID getParkingSpotId() {
        return parkingSpotId;
    }
}
