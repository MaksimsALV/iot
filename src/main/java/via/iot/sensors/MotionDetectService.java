package via.iot.sensors;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.PullResistance;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import via.iot.actuators.Device;
import via.iot.api.dto.SensorDto;
import via.iot.events.EventLogger;

import java.time.Instant;

@Component
public class MotionDetectService {
    public final Context pi4j;
    public DigitalInput pir;
    public final EventLogger eventLogger;
    public SensorDto sensorDto = new SensorDto();

    private boolean readyForNextDetection = true;

    public MotionDetectService(Context pi4j, EventLogger eventLogger) {
        this.pi4j = pi4j;
        this.eventLogger = eventLogger;
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
        boolean currentState = pir.state().isHigh();

        if (!currentState) {
            readyForNextDetection = true;
            return;
        }

        if (readyForNextDetection) {
            Instant now = Instant.now();
            sensorDto.lastMotionDetectedAt = now;

            eventLogger.logEvent(
                    Device.PIR.type,
                    Device.PIR.name(),
                    "motion found at " + now
            );

            readyForNextDetection = false;
        }
    }
}