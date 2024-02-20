package Clients;

import Observing.Observer;
import lombok.Setter;

public class Client implements Observer {
    private String name;
    private String surname;
    @Setter
    private String address;
    private Integer passportSeries;
    private Integer passportNumber;

    public Client(String name, String surname, String address, Integer passportSeries, Integer passportNumber) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
    }

    public void setPassport(Integer passportSeries, Integer passportNumber) {
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
    }

    public boolean isSuspicious() {
        return address == null || passportNumber == 0;
    }

    @Override
    public void modify(String eventType) {
        // Notified
    }
}
