package via.iot.events;

import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "events")
public class EventEntity {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;  //uuid generated

    @Column(nullable = false)
    private String type; //actuator

    @Column(nullable = false)
    private String name; //LED

    @Column(nullable = false)
    private String activity; //ON

    @Column(nullable = false)
    private Instant createdAt;

    protected EventEntity() {}

    public EventEntity(String type, String name, String activity) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.name = name;
        this.activity = activity;
        this.createdAt = Instant.now();
    }
}
