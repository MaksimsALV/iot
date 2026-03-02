package via.iot.events;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class EventLoggerTests {

    @Test
    void logEvent_callsRepositorySave() {
        EventRepository repo = mock(EventRepository.class);
        EventLogger logger = new EventLogger(repo);

        logger.logEvent("actuator", "LED", "ON");

        verify(repo).save(any(EventEntity.class));
    }
}
