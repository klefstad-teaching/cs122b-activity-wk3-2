package edu.uci.ics.iskandea.service.idm.resources;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.iskandea.service.idm.IdmService;
import edu.uci.ics.iskandea.service.idm.logger.ServiceLogger;
import edu.uci.ics.iskandea.service.idm.model.data.UserData;
import edu.uci.ics.iskandea.service.idm.model.request.UserRequestModel;
import edu.uci.ics.iskandea.service.idm.model.response.UserStandardResponseModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("normal")
public class NormalPage
{
    @Path("user")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(String jsonString)
    {
        // Old Model Mapping

        ObjectMapper mapper = new ObjectMapper();
        UserRequestModel requestModel;
        UserStandardResponseModel responseModel = new UserStandardResponseModel();

        ServiceLogger.LOGGER.info("Creating Request Model");
        try {
            requestModel = mapper.readValue(jsonString, UserRequestModel.class);

        } catch (IOException e) {
            if (e instanceof JsonMappingException) {
                responseModel.setMessage("JSON Mapping Exception.");
                responseModel.setResultCode(-2);
                return Response.status(Response.Status.BAD_REQUEST).entity(responseModel).build();

            } else if (e instanceof JsonParseException) {
                responseModel.setMessage("JSON Parse Exception.");
                responseModel.setResultCode(-3);
                return Response.status(Response.Status.BAD_REQUEST).entity(responseModel).build();

            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

            }

        }


        // Old Query Handler

        ServiceLogger.LOGGER.info("Creating SQL Query");
        String query = "\n" +
                "SELECT userName, level, status, logIns \n" +
                "FROM users \n" +
                "WHERE level > ? \n" +
                "    AND status > ? \n" +
                "    AND logIns > ? \n" +
                "    AND userName LIKE ?;";

        try {
            Connection con = IdmService.getCon();
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, requestModel.getLevel());
            ps.setInt(2, requestModel.getStatus());
            ps.setInt(3, requestModel.getLevel());
            ps.setString(4, '%' + requestModel.getUserName() + '%');

            ServiceLogger.LOGGER.info("SQL Query: " + ps.toString());

            ResultSet rs = ps.executeQuery();

            ArrayList<UserData> list = new ArrayList<>();

            while (rs.next()) {
                UserData userData = new UserData();
                    userData.setUserName(rs.getString("userName"));
                    userData.setLevel(rs.getInt("level"));
                    userData.setStatus(rs.getInt("status"));
                    userData.setLogIns(rs.getInt("logIns"));

                list.add(userData);
            }

            responseModel.setUserData(list.toArray(new UserData[0]));

            if (list.size() == 0) {
                ServiceLogger.LOGGER.info("Could not find user");
                responseModel.setMessage("Could not find user.");
                responseModel.setResultCode(11);
            } else {
                ServiceLogger.LOGGER.info("UserFound");
                responseModel.setMessage("User found.");
                responseModel.setResultCode(10);
            }

        } catch (SQLException e) {
            ServiceLogger.LOGGER.info("SQL Exception raised: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

        }

        ServiceLogger.LOGGER.info("Returning with result: " + responseModel.getMessage());
        return Response.status(Response.Status.OK).entity(responseModel).build();
    }
}
