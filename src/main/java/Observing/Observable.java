package Observing;

public interface Observable {
    void subscribe(String eventType, Observer observer);

    void notify(String eventType);
}
