package via.iot.sensors;

import org.springframework.stereotype.Service;
import via.iot.api.dto.SensorDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class Dht11Service {
    public SensorDto sensorDto = new SensorDto();

    public static final Path TEMP_PATH =
            Path.of("/sys/bus/iio/devices/iio:device0/in_temp_input");

    public static final Path HUMIDITY_PATH =
            Path.of("/sys/bus/iio/devices/iio:device0/in_humidityrelative_input");

    public void read() {
        Double temperature = tryReadValue(TEMP_PATH);
        Double humidity = tryReadValue(HUMIDITY_PATH);

        if (temperature != null) {
            sensorDto.temperature = temperature;
        }

        if (humidity != null) {
            sensorDto.humidity = humidity;
        }
    }

    private Double tryReadValue(Path path) {
        try {
            return Integer.parseInt(Files.readString(path).trim()) / 1000.0;
        } catch (IOException | NumberFormatException e) {
            return null;
        }
    }
}