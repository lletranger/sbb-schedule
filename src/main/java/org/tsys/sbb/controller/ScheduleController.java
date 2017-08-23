package org.tsys.sbb.controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.tsys.sbb.dto.ScheduleDto;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.ws.rs.core.MediaType;

@Named("scheduleController")
@Stateless
public class ScheduleController {

    private int id = 4;

    @EJB
    private ScheduleDto scheduleDto;

    public ScheduleDto recieveSchedule() {

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client client = Client.create(clientConfig);
        String url = "http://localhost:8000/schedule/station/" + id;
        WebResource webResource = client.resource(url);

        ClientResponse response = webResource
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        ScheduleDto responseEntity = response.getEntity(ScheduleDto.class);

        if (scheduleDto != responseEntity) {
            scheduleDto = responseEntity;
        }
        return scheduleDto;
    }

    public ScheduleDto getScheduleDto() {
        return scheduleDto;
    }
}
