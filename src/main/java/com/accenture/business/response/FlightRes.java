package com.accenture.business.response;

import com.accenture.business.handler.reflect.TypeReflect;
import com.alibaba.fastjson.JSONObject;

public class FlightRes {

    @TypeReflect(value = "flightNumber")
    private String flightNumber;

    @TypeReflect(value = "originPort")
    private String originPort;

    @TypeReflect(value = "destinationPort")
    private String destinationPort;

    @TypeReflect(value = "aircraft")
    private String aircraft;

    @TypeReflect(value = "departureDate")
    private String departureDate;

    @TypeReflect(value = "status")
    private String status;

    @TypeReflect(value = "scheduledDepartureTime")
    private String scheduledDepartureTime;

    @TypeReflect(value = "scheduledArrivalTime")
    private String scheduledArrivalTime;

    @TypeReflect(value = "estimatedDepartureTime")
    private String estimatedDepartureTime;

    @TypeReflect(value = "estimatedArrivalTime")
    private String estimatedArrivalTime;

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getOriginPort() {
        return originPort;
    }

    public void setOriginPort(String originPort) {
        this.originPort = originPort;
    }

    public String getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScheduledDepartureTime() {
        return scheduledDepartureTime;
    }

    public void setScheduledDepartureTime(String scheduledDepartureTime) {
        this.scheduledDepartureTime = scheduledDepartureTime;
    }

    public String getScheduledArrivalTime() {
        return scheduledArrivalTime;
    }

    public void setScheduledArrivalTime(String scheduledArrivalTime) {
        this.scheduledArrivalTime = scheduledArrivalTime;
    }

    public String getEstimatedDepartureTime() {
        return estimatedDepartureTime;
    }

    public void setEstimatedDepartureTime(String estimatedDepartureTime) {
        this.estimatedDepartureTime = estimatedDepartureTime;
    }

    public String getEstimatedArrivalTime() {
        return estimatedArrivalTime;
    }

    public void setEstimatedArrivalTime(String estimatedArrivalTime) {
        this.estimatedArrivalTime = estimatedArrivalTime;
    }

    public FlightRes(){}

    public FlightRes(JSONObject result){
        this.flightNumber = result.getString("flightNumber");

        this.originPort = result.getString("originPort");

        this.destinationPort = result.getString("destinationPort");

        this.aircraft = result.getString("aircraft");

        this.departureDate = result.getString("departureDate");

        this.status = result.getString("status");

        this.scheduledDepartureTime = result.getString("scheduledDepartureTime");

        this.scheduledArrivalTime = result.getString("scheduledArrivalTime");

        this.estimatedDepartureTime = result.getString("estimatedDepartureTime");

        this.estimatedArrivalTime = result.getString("estimatedArrivalTime");
    }

    @Override
    public String toString() {
        return "FlightRes{" +
                "flightNumber='" + flightNumber + '\'' +
                ", originPort='" + originPort + '\'' +
                ", destinationPort='" + destinationPort + '\'' +
                ", aircraft='" + aircraft + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", status='" + status + '\'' +
                ", scheduledDepartureTime='" + scheduledDepartureTime + '\'' +
                ", scheduledArrivalTime='" + scheduledArrivalTime + '\'' +
                ", estimatedDepartureTime='" + estimatedDepartureTime + '\'' +
                ", estimatedArrivalTime='" + estimatedArrivalTime + '\'' +
                '}';
    }
}
