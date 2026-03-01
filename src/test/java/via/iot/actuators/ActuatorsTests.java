package via.iot.actuators;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import via.iot.api.dto.ServiceDto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ActuatorsTests {
    public LedService ledService;
    public BuzzerService buzzerService;

    public DigitalOutput ledDigitalOutput;
    public DigitalOutput buzzerDigitalOutput;

    public ServiceDto serviceDto;

    @BeforeEach
    void actuatorsInit() {
        ledService = new LedService();
        buzzerService = new BuzzerService();

        ledDigitalOutput = mock(DigitalOutput.class);
        buzzerDigitalOutput = mock(DigitalOutput.class);

        ledService.led = ledDigitalOutput;
        buzzerService.buzzer = buzzerDigitalOutput;

        serviceDto = new ServiceDto();
        serviceDto.ledIsOn = false;
        serviceDto.buzzerIsOn = false;

        ledService.serviceDto = serviceDto;
        buzzerService.serviceDto = serviceDto;
    }

    @Test
    void ifLedIsOnSetFlagTrue() {
        ledService.setLedOn();

        assertTrue(serviceDto.ledIsOn);
        assertFalse(serviceDto.buzzerIsOn);

        verify(ledDigitalOutput).high();
    }

    @Test
    void ifLedIsOffSetFlagFalse() {
        serviceDto.ledIsOn = true;

        ledService.setLedOff();

        assertFalse(serviceDto.ledIsOn);
        assertFalse(serviceDto.buzzerIsOn);

        verify(ledDigitalOutput).low();
    }

    @Test
    void ifBuzzerIsOnSetFlagTrue() {
        buzzerService.setBuzzerOn();

        assertTrue(serviceDto.buzzerIsOn);
        assertFalse(serviceDto.ledIsOn);

        verify(buzzerDigitalOutput).high();
    }

    @Test
    void ifBuzzerIsOffSetFlagFalse() {
        serviceDto.buzzerIsOn = true;

        buzzerService.setBuzzerOff();

        assertFalse(serviceDto.buzzerIsOn);
        assertFalse(serviceDto.ledIsOn);

        verify(buzzerDigitalOutput).low();
    }

    @Test
    void ifBothOnThenBothFlagsTrue() {
        ledService.setLedOn();
        buzzerService.setBuzzerOn();

        assertTrue(serviceDto.ledIsOn);
        assertTrue(serviceDto.buzzerIsOn);

        verify(ledDigitalOutput).high();
        verify(buzzerDigitalOutput).high();
    }

    @Test
    void ifBothOffThenBothFlagsFalse() {
        serviceDto.ledIsOn = true;
        serviceDto.buzzerIsOn = true;

        ledService.setLedOff();
        buzzerService.setBuzzerOff();

        assertFalse(serviceDto.ledIsOn);
        assertFalse(serviceDto.buzzerIsOn);

        verify(ledDigitalOutput).low();
        verify(buzzerDigitalOutput).low();
    }
}
