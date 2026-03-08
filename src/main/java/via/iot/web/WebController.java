package via.iot.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import via.iot.api.ActuatorsController;
import via.iot.api.StatusController;
import via.iot.api.dto.ActuatorsRequestDto;
import via.iot.api.dto.StatusDto;

@Controller
public class WebController {
    private final StatusController statusController;
    private final ActuatorsController actuatorsController;

    public WebController(StatusController statusController, ActuatorsController actuatorsController) {
        this.statusController = statusController;
        this.actuatorsController = actuatorsController;
    }

    @GetMapping("/")
    public String index(Model model) {
        StatusDto dto = statusController.get();
        model.addAttribute("actuators", dto.actuators);
        model.addAttribute("sensors", dto.sensors);
        return "index";
    }

    @PostMapping("/actuators")
    public String actuators(
            @RequestParam String led,
            @RequestParam String buzzer,
            @RequestParam String motor
    ) {
        ActuatorsRequestDto dto = new ActuatorsRequestDto();
        dto.led = led;
        dto.buzzer = buzzer;
        dto.motor = motor;

        actuatorsController.post(dto);

        return "redirect:/";
    }
}