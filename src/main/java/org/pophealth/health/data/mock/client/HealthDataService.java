package org.pophealth.health.data.mock.client;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.pophealth.health.data.mock.model.HealthData;

@Path("/healthRules")
@RegisterRestClient
public interface HealthDataService {

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    void loadHealthData(List<HealthData> data);

}
