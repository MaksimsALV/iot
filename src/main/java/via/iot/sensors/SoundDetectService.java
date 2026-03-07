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
    public final Context pi4j;
    public DigitalInput mic;
    public final EventLogger eventLogger;
    public final LedService ledService;
    public SensorDto sensorDto = new SensorDto();

    private boolean readyForNextDetection = true;
    private Instant soundLedOnUntil;

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
        Instant now = Instant.now();

        if (soundLedOnUntil != null && now.isAfter(soundLedOnUntil)) {
            ledService.setSoundLedOff();
            soundLedOnUntil = null;
        }

        boolean currentState = mic.state().isHigh();

        if (!currentState) {
            readyForNextDetection = true;
            return;
        }

        if (readyForNextDetection) {
            sensorDto.lastSoundDetectedAt = now;

            eventLogger.logEvent(
                    Device.MIC.type,
                    Device.MIC.name(),
                    "sound detected at " + now
            );

            ledService.setSoundLedOn();
            soundLedOnUntil = now.plusSeconds(2);
            readyForNextDetection = false;
        }
    }
}