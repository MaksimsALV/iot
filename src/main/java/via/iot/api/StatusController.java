package via.iot.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import via.iot.actuators.BuzzerService;
import via.iot.actuators.LedService;
import via.iot.actuators.MotorService;
import via.iot.api.dto.SensorDto;
import via.iot.api.dto.ServiceDto;
import via.iot.api.dto.StatusDto;
import via.iot.sensors.Dht11Service;
import via.iot.sensors.MotionDetectService;

@RestController
@RequestMapping("/api/status")
public class StatusController {
    private final LedService ledService;
    private final BuzzerService buzzerService;
    private final MotorService motorService;
    private final Dht11Service dht11Service;
    private final MotionDetectService motionDetectService;

    public StatusController(
            LedService ledService,
            BuzzerService buzzerService,
            MotorService motorService,
            Dht11Service dht11Service,
            MotionDetectService motionDetectService

    ) {
        this.ledService = ledService;
        this.buzzerService = buzzerService;
        this.motorService = motorService;
        this.dht11Service = dht11Service;
        this.motionDetectService = motionDetectService;
    }

    @GetMapping
    public StatusDto get() {
        dht11Service.read();
        motionDetectService.read();

        ServiceDto serviceDto = new ServiceDto();
        serviceDto.ledIsOn = ledService.serviceDto.ledIsOn;
        serviceDto.buzzerIsOn = buzzerService.serviceDto.buzzerIsOn;
        serviceDto.motorIsOn = motorService.serviceDto.motorIsOn;

        SensorDto sensorDto = new SensorDto();
        sensorDto.temperature = dht11Service.sensorDto.temperature;
        sensorDto.humidity = dht11Service.sensorDto.humidity;
        sensorDto.lastMotionDetectedAt = motionDetectService.sensorDto.lastMotionDetectedAt;

        StatusDto dto = new StatusDto();
        dto.actuators = serviceDto;
        dto.sensors = sensorDto;

        return dto;
    }
}