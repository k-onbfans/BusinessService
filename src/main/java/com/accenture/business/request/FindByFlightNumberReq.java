package com.accenture.business.request;

import com.accenture.business.handler.validation.IsValidDate;

import javax.validation.constraints.Pattern;
import java.sql.Date;

public class FindByFlightNumberReq {

    @Pattern(regexp = "^CX{1}[0-9]{3}$",message = "flightNumber is invalid")
    private String flightNumber;

    @IsValidDate
    private Date departureDate;

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }
}
