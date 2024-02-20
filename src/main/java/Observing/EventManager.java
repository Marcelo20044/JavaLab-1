package Observing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager implements Observable {
    Map<String, List<Observer>> observers = new HashMap<>();

    @Override
    public void subscribe(String eventType, Observer observer) {
        observers.get(eventType).add(observer);
    }

    @Override
    public void notify(String eventType) {
        observers.get(eventType).forEach(observer -> observer.modify(eventType));
    }
}
