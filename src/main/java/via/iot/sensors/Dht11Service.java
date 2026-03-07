package via.iot.sensors;

import org.springframework.stereotype.Service;
import via.iot.api.dto.SensorDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class Dht11Service {
    public SensorDto sensorDto = new SensorDto();

    private static final Path TEMP_PATH =
            Path.of("/sys/bus/iio/devices/iio:device0/in_temp_input");

    private static final Path HUMIDITY_PATH =
            Path.of("/sys/bus/iio/devices/iio:device0/in_humidityrelative_input");

    public void read() {
        RuntimeException lastException = null;

        for (int i = 0; i < 5; i++) {
            try {
                int tempRaw = Integer.parseInt(Files.readString(TEMP_PATH).trim());
                int humidityRaw = Integer.parseInt(Files.readString(HUMIDITY_PATH).trim());

                sensorDto.temperature = tempRaw / 1000.0;
                sensorDto.humidity = humidityRaw / 1000.0;
                return;
            } catch (IOException | NumberFormatException e) {
                lastException = new RuntimeException("Failed to read DHT11", e);

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("DHT11 read interrupted", ex);
                }
            }
        }

        throw lastException;
    }
}