package via.iot.api;

import org.springframework.web.bind.annotation.*;
import via.iot.actuators.BuzzerService;
import via.iot.actuators.LedService;
import via.iot.api.dto.ServiceDto;

@RestController
@RequestMapping("/api/actuators")
public class ActuatorsController {
    private final LedService ledService;
    private final BuzzerService buzzerService;

    public ActuatorsController(LedService ledService, BuzzerService buzzerService) {
        this.ledService = ledService;
        this.buzzerService = buzzerService;
    }

    @GetMapping
    public ServiceDto get() {
        ServiceDto dto = new ServiceDto();
        dto.ledIsOn = ledService.serviceDto.ledIsOn;
        dto.buzzerIsOn = buzzerService.serviceDto.buzzerIsOn;
        return dto;
    }

    @PostMapping
    public ServiceDto post(@RequestBody ServiceDto serviceDto) {
        if (serviceDto.ledIsOn) {
            ledService.setLedOn();
        } else {
            ledService.setLedOff();
        }

        if (serviceDto.buzzerIsOn) {
        buzzerService.setBuzzerOn();
        } else {
            buzzerService.setBuzzerOff();
        }

        ServiceDto dto = new ServiceDto();
        dto.ledIsOn = ledService.serviceDto.ledIsOn;
        dto.buzzerIsOn = buzzerService.serviceDto.buzzerIsOn;
        return dto;
    }
}
