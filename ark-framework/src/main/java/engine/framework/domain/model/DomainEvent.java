package engine.framework.domain.model;

import java.time.LocalDateTime;

/**
 * @Description:
 * @Author: Noah Zhou
 */
public class DomainEvent {

    private final LocalDateTime occurredAt = LocalDateTime.now();

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
}
