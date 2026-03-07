package via.iot.actuators;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import via.iot.api.dto.ServiceDto;

@Component
public class ServoService {
    public ServiceDto serviceDto = new ServiceDto();

    @PostConstruct
    public void initializer() {
        serviceDto.servoAngle = 90;
    }

    public void setAngle(int angle) {
        if (angle < 0) angle = 0;
        if (angle > 180) angle = 180;

        try {
            Process process = new ProcessBuilder(
                    "python3",
                    "/opt/iot/servo.py",
                    String.valueOf(angle)
            ).redirectErrorStream(true).start();

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("servo.py failed with exit code " + exitCode);
            }

            serviceDto.servoAngle = angle;
            System.out.println("Servo moved to " + angle);
        } catch (Exception e) {
            throw new RuntimeException("Failed to move servo", e);
        }
    }
}