import java.util.UUID;

public abstract class Vehicle {


    private final UUID id;

    private Size size;

    public Vehicle(Size size) {
        this.id = UUID.randomUUID();
        this.size = size;
    }

    public Size getSize() {
        return size;
    }


    public UUID getId() {
        return id;
    }
}
