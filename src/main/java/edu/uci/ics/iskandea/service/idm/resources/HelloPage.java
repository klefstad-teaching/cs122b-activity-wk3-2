package edu.uci.ics.iskandea.service.idm.resources;

import edu.uci.ics.iskandea.service.idm.model.data.Message;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("hello")
public class HelloPage
{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response postHello()
    {
        return Response.status(Response.Status.OK)
                .entity(new Message("GET: api/idm/hello")).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHello()
    {
        return Response.status(Response.Status.OK)
                .entity(new Message("POST: api/idm/hello")).build();
    }
}
