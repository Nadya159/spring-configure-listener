package by.javaguru.listener.entity;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EntityEvent extends ApplicationEvent {
    private final AccessType accessType;

    public EntityEvent(Object entity, AccessType accessType){
        super(entity);
        this.accessType = accessType;
    }
}
