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
public class SoundDetectService {
    private final Context pi4j;
    private final EventLogger eventLogger;
    private final LedService ledService;

    private DigitalInput mic;
    public SensorDto sensorDto = new SensorDto();

    private Instant lastDetectionTime = Instant.EPOCH;

    public SoundDetectService(Context pi4j, EventLogger eventLogger, LedService ledService) {
        this.pi4j = pi4j;
        this.eventLogger = eventLogger;
        this.ledService = ledService;
    }

    @PostConstruct
    public void initializer() {
        mic = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                .id("mic")
                .address(6)
                .pull(PullResistance.PULL_DOWN)
                .build());

        sensorDto.lastSoundDetectedAt = null;

        Thread detectorThread = new Thread(() -> {
            while (true) {
                try {
                    read();
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        detectorThread.setDaemon(true);
        detectorThread.start();
    }

    public void read() {
        if (mic.state().isHigh()) {
            Instant now = Instant.now();

            if (now.isBefore(lastDetectionTime.plusSeconds(2))) {
                return;
            }

            lastDetectionTime = now;
            sensorDto.lastSoundDetectedAt = now;

            eventLogger.logEvent(
                    Device.MIC.type,
                    Device.MIC.name(),
                    "sound detected at " + now
            );

            ledService.setSoundLedOn();

            new Thread(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                ledService.setSoundLedOff();
            }).start();
        }
    }
}