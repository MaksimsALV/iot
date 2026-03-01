package via.iot.actuators;
import com.pi4j.io.gpio.digital.DigitalOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import via.iot.api.dto.ServiceDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LedServiceTests {
    public LedService s;
    public DigitalOutput digitalOutput;

    @BeforeEach
    void ledServiceInit() {
        s = new LedService();
        digitalOutput = mock(DigitalOutput.class);
        s.led = digitalOutput;

        s.serviceDto = new ServiceDto();
        s.serviceDto.ledIsOn = false;
    }

    @Test
    void ifLedIsOnSetFlagTrue() {
        s.setLedOn();
        assertTrue(s.serviceDto.ledIsOn);
        verify(digitalOutput).high();
    }

    @Test
    void ifLedIsOffSetFlagFalse() {
        s.serviceDto.ledIsOn = false;
        s.setLedOff();
        assertFalse(s.serviceDto.ledIsOn);
        verify(digitalOutput).low();
    }
}
