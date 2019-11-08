package com.accenture.business.service.v2.impl;

import com.accenture.business.constant.CacheNameConstants;
import com.accenture.business.handler.aop.LogTime;
import com.accenture.business.request.FindByFlightNumberReq;
import com.accenture.business.request.FindByRouteReq;
import com.accenture.business.service.HttpService;
import com.accenture.business.service.v2.AsyncService;
import com.accenture.business.utils.JSONUtil;
import com.accenture.business.bean.Port;
import com.accenture.business.response.v1.FlightV1Res;
import com.accenture.business.response.v1.FlightV1Reses;
import com.accenture.business.service.v2.FlightStatusV2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;

@Service
@EnableCaching
public class FlightStatusV2ServiceImpl implements FlightStatusV2Service {

    @Autowired
    private HttpService httpUtil;

    @Autowired
    private AsyncService asyncService;


    @Value("${flightStatusByFDV1Url}")
    private String flightStatusUrl;

    @Value("${flightTimeByFDV1Url}")
    private String flightTimeUrl;

    @Value("${flightInfoByODV1Url}")
    private String flightInfoByRouteUrl;

    @Override
    @LogTime
//    @Cacheable(cacheNames= CacheNameConstants.ASYNC,keyGenerator = "keyGenerator")
    public FlightV1Reses searchByRoute(FindByRouteReq request){
        FlightV1Reses reses = new FlightV1Reses();
        Port port = new Port();
        port.setDestination(request.getDestinationPort());
        port.setOrigin(request.getOriginPort());
        String list = httpUtil.postFlightByRouteV1(flightInfoByRouteUrl,port);
        reses = JSONUtil.stringToDTO(list,reses,FlightV1Reses.class);
        FindByFlightNumberReq req = new FindByFlightNumberReq();
        req.setDepartureDate(request.getDepartureDate());
        List<FlightV1Res> resList = reses.getList();
        List<Future<FlightV1Res>> futures = new ArrayList<>();
        for(FlightV1Res res : resList){
            Future<FlightV1Res> future = asyncService.getStatusAndTime(req,res);
            futures.add(future);
        }

        while (!futures.isEmpty()){
            Iterator<Future<FlightV1Res>> iterator = futures.iterator();
            while (iterator.hasNext()){
                Future<FlightV1Res> temp = iterator.next();
                if(temp.isDone()){
                    iterator.remove();
                }
            }
        }
        reses.setList(resList);
        return reses;
    }

}
