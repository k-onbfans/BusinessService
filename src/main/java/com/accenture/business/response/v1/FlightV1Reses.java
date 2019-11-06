package com.accenture.business.response.v1;

import java.util.List;

public class FlightV1Reses {

    List<FlightV1Res> list;

    public FlightV1Reses(List<FlightV1Res> list) {
        this.list = list;
    }

    public FlightV1Reses(){}

    public List<FlightV1Res> getList() {
        return list;
    }

    public void setList(List<FlightV1Res> list) {
        this.list = list;
    }
}
