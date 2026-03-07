package via.iot.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import via.iot.actuators.BuzzerService;
import via.iot.actuators.Device;
import via.iot.actuators.LedService;
import via.iot.actuators.ServoService;
import via.iot.api.dto.SensorDto;
import via.iot.api.dto.ServiceDto;
import via.iot.api.dto.StatusDto;
import via.iot.events.EventLogger;
import via.iot.sensors.Dht11Service;

@RestController
@RequestMapping("/api/status")
public class StatusController {
    private final LedService ledService;
    private final BuzzerService buzzerService;
    private final ServoService servoService;
    private final Dht11Service dht11Service;
    private final EventLogger eventLogger;

    public StatusController(
            LedService ledService,
            BuzzerService buzzerService,
            ServoService servoService,
            Dht11Service dht11Service,
            EventLogger eventLogger
    ) {
        this.ledService = ledService;
        this.buzzerService = buzzerService;
        this.servoService = servoService;
        this.dht11Service = dht11Service;
        this.eventLogger = eventLogger;
    }

    @GetMapping
    public StatusDto get() {
        dht11Service.read();

        ServiceDto serviceDto = new ServiceDto();
        serviceDto.ledIsOn = ledService.serviceDto.ledIsOn;
        serviceDto.buzzerIsOn = buzzerService.serviceDto.buzzerIsOn;
        serviceDto.servoAngle = servoService.serviceDto.servoAngle;

        SensorDto sensorDto = new SensorDto();
        sensorDto.temperature = dht11Service.sensorDto.temperature;
        sensorDto.humidity = dht11Service.sensorDto.humidity;

        eventLogger.logEvent(Device.DHT11.type, Device.DHT11.name(),
                "TEMPERATURE=" + sensorDto.temperature + ", HUMIDITY=" + sensorDto.humidity);

        StatusDto dto = new StatusDto();
        dto.actuators = serviceDto;
        dto.sensors = sensorDto;

        return dto;
    }
}