package com.accenture.business.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class FindByRouteReq {
    private String originPort;
    private String destinationPort;
    private Date departureDate;
}
