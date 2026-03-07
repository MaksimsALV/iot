package via.iot.actuators;

public enum Device {
    LED("Actuator"),
    BUZZER("Actuator"),
    SERVO("Actuator");

    public final String type;
    Device(String type) {
        this.type = type;
    }
}
