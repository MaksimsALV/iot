package via.iot.actuators;

public enum Device {
    LED("Actuator"),
    BUZZER("Actuator"),
    MOTOR("Actuator"),
    DHT11("Sensor"),
    PIR("Sensor"),
    MIC("Sensor");

    public final String type;
    Device(String type) {
        this.type = type;
    }
}
