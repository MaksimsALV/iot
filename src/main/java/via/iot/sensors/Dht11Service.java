package via.iot.sensors;

import org.springframework.stereotype.Service;
import via.iot.actuators.Device;
import via.iot.api.dto.SensorDto;
import via.iot.events.EventLogger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class Dht11Service {
    public SensorDto sensorDto = new SensorDto();
    private final EventLogger eventLogger;


    public static final Path TEMP_PATH =
            Path.of("/sys/bus/iio/devices/iio:device0/in_temp_input");

    public static final Path HUMIDITY_PATH =
            Path.of("/sys/bus/iio/devices/iio:device0/in_humidityrelative_input");

    public Dht11Service(EventLogger eventLogger) {
        this.eventLogger = eventLogger;
    }

    public void read() {
        Double temperature = tryReadValue(TEMP_PATH);
        Double humidity = tryReadValue(HUMIDITY_PATH);

        if (temperature != null) {
            sensorDto.temperature = temperature;
        }

        if (humidity != null) {
            sensorDto.humidity = humidity;
        }

        eventLogger.logEvent(Device.DHT11.type, Device.DHT11.name(),
                "TEMPERATURE=" + sensorDto.temperature + ", HUMIDITY=" + sensorDto.humidity);
    }

    private Double tryReadValue(Path path) {
        try {
            return Integer.parseInt(Files.readString(path).trim()) / 1000.0;
        } catch (IOException | NumberFormatException e) {
            return null;
        }
    }
}