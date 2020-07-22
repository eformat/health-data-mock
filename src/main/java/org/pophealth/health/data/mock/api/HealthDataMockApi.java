package org.pophealth.health.data.mock.api;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.pophealth.health.data.mock.model.MockDataResult;

@Path("/mock")
public class HealthDataMockApi {

   @Inject
   MockDataParser parser;

    @POST
    @Path("/loadDefaultData")
    @Produces(MediaType.APPLICATION_JSON)
    public MockDataResult loadDefaultData(){

        parser.loadData();

        MockDataResult result = new MockDataResult();
        result.setResult("Success");
        result.setTotalLoaded(1);
        return result;

    }
}
