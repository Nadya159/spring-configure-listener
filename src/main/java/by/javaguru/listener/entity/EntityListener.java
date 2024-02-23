package by.javaguru.listener.entity;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EntityListener {
    @EventListener(condition = "#root.args[0].accessType.name() == 'READ'")
    public void acceptEntityRead(EntityEvent entityEvent) {
        System.out.println("Entity (READ): " + entityEvent);
    }

    @EventListener(condition = "#root.args[0].accessType.name() == 'CREATE'")
    public void acceptEntityCreate(EntityEvent entityEvent) {
        System.out.println("Entity (CREATE): " + entityEvent);
    }

    @EventListener(condition = "#root.args[0].accessType.name() == 'UPDATE'")
    public void acceptEntityUpdate(EntityEvent entityEvent) {
        System.out.println("Entity (UPDATE): " + entityEvent);
    }

    @EventListener(condition = "#root.args[0].accessType.name() == 'DELETE'")
    public void acceptEntityDelete(EntityEvent entityEvent) {
        System.out.println("Entity (DELETE): " + entityEvent);
    }
}
