package via.iot.actuators;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import via.iot.api.dto.ServiceDto;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
public class MotorService {
    public ServiceDto serviceDto = new ServiceDto();
    public Process motor;
    public Path servoScriptPath;

    @PostConstruct
    public void initializer() {
        servoScriptPath = extractServoScript();
        serviceDto.motorIsOn = false;
    }

    public void setMotorOn() {
        motor = startMotorProcess();
        updateMotorIsOn(true);
    }

    public void setMotorOff() {
        if (motor != null) {
            motor.destroy();
        }
        updateMotorIsOn(false);
    }

    public void updateMotorIsOn(boolean value) {
        serviceDto.motorIsOn = value;
    }

    @PreDestroy
    public void cleanup() {
        if (motor != null) {
            motor.destroy();
        }
    }

    private Process startMotorProcess() {
        try {
            return new ProcessBuilder(
                    "python3",
                    servoScriptPath.toString()
            ).redirectErrorStream(true).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path extractServoScript() {
        try (InputStream inputStream = getClass().getResourceAsStream("/python/servo.py")) {
            Path tempFile = Files.createTempFile("servo-", ".py");
            Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
            tempFile.toFile().deleteOnExit();
            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}