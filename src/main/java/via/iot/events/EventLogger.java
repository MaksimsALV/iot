package via.iot.events;

import org.springframework.stereotype.Component;

@Component
public class EventLogger {
    private final EventRepository eventRepository;

    public EventLogger(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void logEvent(String type, String name, String activity) {
        eventRepository.save(new EventEntity(type, name, activity));
    }
}
