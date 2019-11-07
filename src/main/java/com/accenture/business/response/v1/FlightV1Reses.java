package com.accenture.business.response.v1;

import java.io.Serializable;
import java.util.List;

/**
 * @author yifei.zhu
 */
public class FlightV1Reses implements Serializable {


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
