package via.iot.api.dto;

import java.time.Instant;

public class SensorDto {
    public Double temperature;
    public Double humidity;
    public Instant lastMotionDetectedAt;
    public Instant lastSoundDetectedAt;
}
