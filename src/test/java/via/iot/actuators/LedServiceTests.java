package via.iot.actuators;
import com.pi4j.io.gpio.digital.DigitalOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LedServiceTests {
    public LedService s;

    @BeforeEach
    void ledServiceInit() {
        s = new LedService();
        s.led = mock(DigitalOutput.class);
    }

    @Test
    void ifLedIsOnSetFlagTrue() {
        s.ledOn();
        assertTrue(s.ledIsOn);
    }

    @Test
    void ifLedIsOffSetFlagFalse() {
        s.ledOff();
        assertFalse(s.ledIsOn);
    }
}
