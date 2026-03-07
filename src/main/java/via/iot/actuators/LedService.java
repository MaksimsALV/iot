package via.iot.actuators;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import via.iot.api.dto.ServiceDto;

@Component
public class LedService {
//    public Context pi4j;
    public final Context pi4j;
    public DigitalOutput led;
    public DigitalOutput statusLed;
    public DigitalOutput motionLed;
    public DigitalOutput soundLed;
    public ServiceDto serviceDto = new ServiceDto();

    public LedService(Context pi4j) {
        this.pi4j = pi4j;
    }

    @PostConstruct
    public void initializer() {
//        pi4j = Pi4J.newAutoContext();
        led = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
                .id("led")
                .address(17)
                .initial(DigitalState.LOW)
                .shutdown(DigitalState.LOW)
                .build());

        statusLed = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
                .id("status-led")
                .address(26)
                .initial(DigitalState.HIGH)
                .shutdown(DigitalState.LOW)
                .build());

        motionLed = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
                .id("motion-led")
                .address(19)
                .initial(DigitalState.LOW)
                .shutdown(DigitalState.LOW)
                .build());

        soundLed = pi4j.create(DigitalOutput.newConfigBuilder(pi4j)
                .id("sound-led")
                .address(13)
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

    public void setMotionLedOn() {
        motionLed.high();
    }

    public void setMotionLedOff() {
        motionLed.low();
    }

    public void setSoundLedOn() {
        soundLed.high();
    }

    public void setSoundLedOff() {
        soundLed.low();
    }

    public void updateLedIsOn(boolean value) {
        serviceDto.ledIsOn = value;
    }
}
