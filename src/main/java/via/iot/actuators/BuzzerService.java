package via.iot.actuators;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import via.iot.api.dto.ServiceDto;

@Component
public class BuzzerService {
    public Context pi4j;
    public DigitalOutput buzzer;
    public ServiceDto serviceDto = new ServiceDto();

    @PostConstruct
    public void initializer() {
        pi4j = Pi4J.newAutoContext();
        buzzer = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
                .id("buzzer")
                .address(27)
                .initial(DigitalState.LOW)
                .shutdown(DigitalState.LOW)
                .build());

        serviceDto.buzzerIsOn = false;
    }

    public void setBuzzerOn() {
        buzzer.high();
        updateBuzzerIsOn(true);
    }

    public void setBuzzerOff() {
        buzzer.low();
        updateBuzzerIsOn(false);
    }

    public void updateBuzzerIsOn(boolean value) {
        serviceDto.buzzerIsOn = value;
    }
}
