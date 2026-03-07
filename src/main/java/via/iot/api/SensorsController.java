package via.iot.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import via.iot.actuators.Device;
import via.iot.api.dto.SensorDto;
import via.iot.events.EventLogger;
import via.iot.sensors.Dht11Service;

@RestController
@RequestMapping("/api/sensors")
public class SensorsController {
    private final Dht11Service dht11Service;
    private final EventLogger eventLogger;

    public SensorsController(Dht11Service dht11Service,  EventLogger eventLogger) {
        this.dht11Service = dht11Service;
        this.eventLogger = eventLogger;
    }

    @GetMapping
    public SensorDto get() {
        dht11Service.read();

        SensorDto dto = new SensorDto();
        dto.temperature = dht11Service.sensorDto.temperature;
        dto.humidity = dht11Service.sensorDto.humidity;
        eventLogger.logEvent(Device.DHT11.type, Device.DHT11.name(),
                "TEMPERATURE=" + dto.temperature + ", HUMIDITY=" + dto.humidity);
        return dto;
    }
}