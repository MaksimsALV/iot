package via.iot.actuators;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import via.iot.api.dto.ServiceDto;

@Component
public class ServoService {
    public ServiceDto serviceDto = new ServiceDto();
    private Process motor;

    @PostConstruct
    public void initializer() {
        serviceDto.motorIsOn = false;
    }

    public void setMotorOn() {
        if (motor != null && motor.isAlive()) {
            updateMotorIsOn(true);
            return;
        }

        try {
            motor = new ProcessBuilder(
                    "python3",
                    "/opt/iot/servo.py"
            ).redirectErrorStream(true).start();

            updateMotorIsOn(true);
        } catch (Exception e) {
            throw new RuntimeException("Failed to start motor", e);
        }
    }
    public void setMotorOff() {
        try {
            if (motor != null && motor.isAlive()) {
                motor.destroy();
                motor.waitFor();
            }

            updateMotorIsOn(false);
        } catch (Exception e) {
            throw new RuntimeException("Failed to stop motor", e);
        }
    }

    public void updateMotorIsOn(boolean value) {
        serviceDto.motorIsOn = value;
    }

    @PreDestroy
    public void cleanup() {
        setMotorOff();
    }
}