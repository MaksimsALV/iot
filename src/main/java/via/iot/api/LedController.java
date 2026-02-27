package via.iot.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import via.iot.actuators.LedService;
import via.iot.api.dto.LedServiceDto;

@RestController
@RequestMapping("/api/led")
public class LedController {
    private final LedService ledService;
    public LedController(LedService ledService) {
        this.ledService = ledService;
    }

    @PostMapping("/on")
    public LedServiceDto on() {
        ledService.ledOn();
        LedServiceDto dto = new LedServiceDto();
        dto.ledIsOn = ledService.ledIsOn;
        return dto;
    }

    @PostMapping("/off")
    public LedServiceDto off() {
        ledService.ledOff();
        LedServiceDto dto = new LedServiceDto();
        dto.ledIsOn = ledService.ledIsOn;
        return dto;
    }

    @GetMapping
    public LedServiceDto get() {
        LedServiceDto dto = new LedServiceDto();
        dto.ledIsOn = ledService.ledIsOn;
        return dto;
    }
}
