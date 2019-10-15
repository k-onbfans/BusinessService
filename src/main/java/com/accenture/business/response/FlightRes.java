package com.accenture.business.response;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
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
}
