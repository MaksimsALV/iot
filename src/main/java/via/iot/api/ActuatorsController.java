package via.iot.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import via.iot.actuators.BuzzerService;
import via.iot.actuators.Device;
import via.iot.actuators.LedService;
import via.iot.actuators.MotorService;
import via.iot.api.dto.ActuatorsRequestDto;
import via.iot.events.EventLogger;

@RestController
@RequestMapping("/api/actuators")
public class ActuatorsController {
    private final LedService ledService;
    private final BuzzerService buzzerService;
    private final MotorService motorService;
    private final EventLogger eventLogger;

    public ActuatorsController(LedService ledService, BuzzerService buzzerService, MotorService motorService, EventLogger eventLogger) {
        this.ledService = ledService;
        this.buzzerService = buzzerService;
        this.motorService = motorService;
        this.eventLogger = eventLogger;
    }

    @PostMapping
    public ResponseEntity<String> post(@RequestBody ActuatorsRequestDto request) {
        if (request.led != null) {
            if ("on".equalsIgnoreCase(request.led)) {
                ledService.setLedOn();
                eventLogger.logEvent(Device.LED.type, Device.LED.name(), "ON");
            } else if ("off".equalsIgnoreCase(request.led)) {
                ledService.setLedOff();
                eventLogger.logEvent(Device.LED.type, Device.LED.name(), "OFF");

            }
        }

        if (request.buzzer != null) {
            if ("on".equalsIgnoreCase(request.buzzer)) {
                buzzerService.setBuzzerOn();
                eventLogger.logEvent(Device.BUZZER.type, Device.BUZZER.name(), "ON");
            } else if ("off".equalsIgnoreCase(request.buzzer)) {
                buzzerService.setBuzzerOff();
                eventLogger.logEvent(Device.BUZZER.type, Device.BUZZER.name(), "OFF");
            }
        }

        if (request.motor != null) {
            if ("on".equalsIgnoreCase(request.motor)) {
                motorService.setMotorOn();
                eventLogger.logEvent(Device.MOTOR.type, Device.MOTOR.name(), "ON");
            } else if ("off".equalsIgnoreCase(request.motor)) {
                motorService.setMotorOff();
                eventLogger.logEvent(Device.MOTOR.type, Device.MOTOR.name(), "OFF");
            }

        }

        return ResponseEntity.ok("success");
    }
}
