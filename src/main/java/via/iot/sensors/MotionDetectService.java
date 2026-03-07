package via.iot.sensors;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.PullResistance;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import via.iot.actuators.Device;
import via.iot.actuators.LedService;
import via.iot.api.dto.SensorDto;
import via.iot.events.EventLogger;

import java.time.Instant;

@Component
public class MotionDetectService {
    public final Context pi4j;
    public DigitalInput pir;
    public final EventLogger eventLogger;
    public final LedService ledService;
    public SensorDto sensorDto = new SensorDto();

    private boolean readyForNextDetection = true;
    private Instant motionLedOnUntil;

    public MotionDetectService(Context pi4j, EventLogger eventLogger, LedService ledService) {
        this.pi4j = pi4j;
        this.eventLogger = eventLogger;
        this.ledService = ledService;
    }

    @PostConstruct
    public void initializer() {
        pir = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                .id("pir")
                .address(22)
                .pull(PullResistance.PULL_DOWN)
                .build());

        sensorDto.lastMotionDetectedAt = null;
    }

    public void read() {
        Instant now = Instant.now();
        if (motionLedOnUntil != null && now.isAfter(motionLedOnUntil)) {
            ledService.setMotionLedOff();
            motionLedOnUntil = null;
        }

        boolean currentState = pir.state().isHigh();

        if (!currentState) {
            readyForNextDetection = true;
            return;
        }

        if (readyForNextDetection) {
            sensorDto.lastMotionDetectedAt = now;

            eventLogger.logEvent(
                    Device.PIR.type,
                    Device.PIR.name(),
                    "motion found at " + now
            );

            ledService.setMotionLedOn();
            motionLedOnUntil = now.plusSeconds(2);
            readyForNextDetection = false;
        }
    }
}