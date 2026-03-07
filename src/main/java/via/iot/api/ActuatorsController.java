package via.iot.api;

import org.springframework.web.bind.annotation.*;
import via.iot.actuators.BuzzerService;
import via.iot.actuators.Device;
import via.iot.actuators.LedService;
import via.iot.actuators.ServoService;
import via.iot.api.dto.ServiceDto;
import via.iot.events.EventLogger;

@RestController
@RequestMapping("/api/actuators")
public class ActuatorsController {
    private final LedService ledService;
    private final BuzzerService buzzerService;
    private final ServoService servoService;
    private final EventLogger eventLogger;

    public ActuatorsController(LedService ledService, BuzzerService buzzerService, ServoService servoService, EventLogger eventLogger) {
        this.ledService = ledService;
        this.buzzerService = buzzerService;
        this.servoService = servoService;
        this.eventLogger = eventLogger;
    }

    @GetMapping
    public ServiceDto get() {
        ServiceDto dto = new ServiceDto();
        dto.ledIsOn = ledService.serviceDto.ledIsOn;
        dto.buzzerIsOn = buzzerService.serviceDto.buzzerIsOn;
        dto.servoAngle = servoService.serviceDto.servoAngle;
        return dto;
    }

    @PostMapping
    public ServiceDto post(@RequestBody ServiceDto serviceDto) {
        if (serviceDto.ledIsOn != null) {
            if (serviceDto.ledIsOn) {
                ledService.setLedOn();
                eventLogger.logEvent(Device.LED.type, Device.LED.name(), "ON");
            } else {
                ledService.setLedOff();
                eventLogger.logEvent(Device.LED.type, Device.LED.name(), "OFF");

            }
        }

        if (serviceDto.buzzerIsOn != null) {
            if (serviceDto.buzzerIsOn) {
                buzzerService.setBuzzerOn();
                eventLogger.logEvent(Device.BUZZER.type, Device.BUZZER.name(), "ON");
            } else {
                buzzerService.setBuzzerOff();
                eventLogger.logEvent(Device.BUZZER.type, Device.BUZZER.name(), "OFF");
            }
        }

        if (serviceDto.servoAngle != null) {
            servoService.setAngle(serviceDto.servoAngle);
            eventLogger.logEvent(Device.SERVO.type, Device.SERVO.name(), String.valueOf(serviceDto.servoAngle));
        }

        ServiceDto dto = new ServiceDto();
        dto.ledIsOn = ledService.serviceDto.ledIsOn;
        dto.buzzerIsOn = buzzerService.serviceDto.buzzerIsOn;
        dto.servoAngle = servoService.serviceDto.servoAngle;
        return dto;
    }
}
