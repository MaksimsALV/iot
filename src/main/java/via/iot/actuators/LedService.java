package via.iot.actuators;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import via.iot.api.dto.ServiceDto;

@Component
public class LedService {
    public Context pi4j;
    public DigitalOutput led;
    public ServiceDto serviceDto = new ServiceDto();

    @PostConstruct
    public void initializer() {
        pi4j = Pi4J.newAutoContext();
        led = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
                .id("led")
                .address(17)
                .initial(DigitalState.LOW)
                .shutdown(DigitalState.LOW)
                .build());

        serviceDto.ledIsOn = false;
    }

    public void setLedOn() {
        led.high();
        updateLedIsOn(true);
    }

    public void setLedOff() {
        led.low();
        updateLedIsOn(false);
    }

    public void updateLedIsOn(boolean value) {
        serviceDto.ledIsOn = value;
    }
}
