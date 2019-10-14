package com.accenture.business.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class FindByFlightNumberReq {

    private String flightNumber;

    private Date departureDate;
}
