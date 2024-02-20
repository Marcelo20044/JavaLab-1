package Clients.Builders;

import Clients.Client;
import Exceptions.ClientRegistrationException;

public class ClientBuilder {
    private String name;
    private String surname;
    private String address;
    private Integer passportSeries;
    private Integer passportNumber;

    public ClientBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ClientBuilder withSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public ClientBuilder withAddress(String address) {
        if (name == null || surname == null)
            throw new ClientRegistrationException("Name and surname must be filled out first");
        this.address = address;
        return this;
    }

    public ClientBuilder witPassport(Integer series, Integer number) {
        if (name == null || surname == null || address == null)
            throw new ClientRegistrationException("Name, surname and address must be filled out first");
        this.passportSeries = series;
        this.passportNumber = number;
        return this;
    }

    public ClientBuilder reset() {
        name = surname = address = null;
        passportSeries = passportNumber = 0;
        return this;
    }

    public Client build() {
        return new Client(name, surname, address, passportSeries, passportNumber);
    }
}
