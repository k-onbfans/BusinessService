package com.accenture.business.response.v1;

import java.io.Serializable;
import java.util.List;

/**
 * @author yifei.zhu
 */
public class FlightV1Reses implements Serializable {


    private static final long serialVersionUID = -2351135615677852537L;

    private List<FlightV1Res> list;

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
