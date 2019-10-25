package com.accenture.business.response;

import com.alibaba.fastjson.JSONObject;

public class FlightRes {
    private String flightNumber;
    private String originPort;
    private String destinationPort;
    private String aircraft;
    private String departureDate;
    private String status;
    private String scheduledDepartureTime;
    private String scheduledArrivalTime;
    private String estimatedDepartureTime;
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
