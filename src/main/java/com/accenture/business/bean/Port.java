package com.accenture.business.bean;

import java.io.Serializable;
import java.util.Objects;

public class Port implements Serializable {

    private String origin;
    private String destination;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "port{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Port port = (Port) o;
        return Objects.equals(origin, port.origin) &&
                Objects.equals(destination, port.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, destination);
    }
}
