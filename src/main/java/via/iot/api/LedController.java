package via.iot.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import via.iot.actuators.LedService;
import via.iot.api.dto.ServiceDto;

@RestController
@RequestMapping("/api/led")
public class LedController {
    private final LedService ledService;
    public LedController(LedService ledService) {
        this.ledService = ledService;
    }

    @PostMapping("/on")
    public ServiceDto on() {
        ledService.setLedOn();
        ServiceDto dto = new ServiceDto();
        dto.ledIsOn = ledService.serviceDto.ledIsOn;
        return dto;
    }

    @PostMapping("/off")
    public ServiceDto off() {
        ledService.setLedOff();
        ServiceDto dto = new ServiceDto();
        dto.ledIsOn = ledService.serviceDto.ledIsOn;
        return dto;
    }

    @GetMapping
    public ServiceDto get() {
        ServiceDto dto = new ServiceDto();
        dto.ledIsOn = ledService.serviceDto.ledIsOn;
        return dto;
    }
}
