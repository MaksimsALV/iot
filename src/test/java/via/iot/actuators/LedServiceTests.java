package via.iot.actuators;
import com.pi4j.io.gpio.digital.DigitalOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        s.ledIsOn = false;
    }

    @Test
    void ifLedIsOnSetFlagTrue() {
        s.ledOn();
        assertTrue(s.ledIsOn);
        verify(digitalOutput).high();
    }

    @Test
    void ifLedIsOffSetFlagFalse() {
        s.ledIsOn = false;
        s.ledOff();
        assertFalse(s.ledIsOn);
        verify(digitalOutput).low();
    }
}
