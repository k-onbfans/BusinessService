package com.accenture.business;

import com.accenture.business.controller.v1.FlightStatusController;
import com.accenture.business.service.v1.FlightStatusService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BussinessServiceApplicationTests {

    @InjectMocks
    private FlightStatusController flightStatusController;

    @Mock
    private FlightStatusService flightStatusService;

    @Test
    public void contextLoads() {
    }

}
