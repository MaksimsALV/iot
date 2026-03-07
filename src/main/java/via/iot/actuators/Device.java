package via.iot.actuators;

public enum Device {
    LED("Actuator"),
    BUZZER("Actuator"),
    SERVO("Actuator"),
    DHT11("Sensor");

    public final String type;
    Device(String type) {
        this.type = type;
    }
}
