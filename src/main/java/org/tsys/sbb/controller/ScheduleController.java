package org.tsys.sbb.controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tsys.sbb.dto.ScheduleDto;

import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.ws.rs.core.MediaType;
import java.util.Calendar;

@Named("scheduleController")
@Singleton
@ApplicationScoped
public class ScheduleController {

    private int id = 4;

    private ScheduleDto scheduleDto;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleController.class);

    public void receiveSchedule() {

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        String url = "http://localhost:8000/schedule/station/" + id;
        WebResource webResource = client.resource(url);

        ClientResponse response = webResource
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        ScheduleDto newScheduleDto = response.getEntity(ScheduleDto.class);
        if (newScheduleDto != null) {
            scheduleDto = newScheduleDto;
        }

        LOGGER.info("Updated");
    }

    public ScheduleDto getNewScheduleDto(){

        if (Calendar.getInstance().get(Calendar.SECOND) < 1) {
            LOGGER.info("Time to update!");
            receiveSchedule();
        }

        return scheduleDto;
    }

    public ScheduleDto getScheduleDto() {
        return scheduleDto;
    }

    public ScheduleController() {
        LOGGER.info("Creating controller");
        receiveSchedule();
    }

    public String setId(int id) {
        this.id = id;
        LOGGER.info("ID changed to {}", id);
        receiveSchedule();
        return "schedule";
    }
}
