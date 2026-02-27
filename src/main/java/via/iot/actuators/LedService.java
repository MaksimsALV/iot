package via.iot.actuators;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class LedService {
    public Context pi4j;
    public DigitalOutput led;
    public boolean ledIsOn;

    @PostConstruct
    public void initializer() {
        pi4j = Pi4J.newAutoContext();
        led = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
                .id("led")
                .address(17)
                .initial(DigitalState.LOW)
                .shutdown(DigitalState.LOW)
                .build());
    }
    public void ledOn() {
        led.high();
        ledIsOn = true;
    }
    public void ledOff() {
        led.low();
        ledIsOn = false;
    }
}
