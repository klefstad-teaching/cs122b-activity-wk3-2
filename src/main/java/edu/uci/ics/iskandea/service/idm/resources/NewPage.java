package edu.uci.ics.iskandea.service.idm.resources;

import edu.uci.ics.iskandea.service.idm.base.Result;
import edu.uci.ics.iskandea.service.idm.model.data.UserData;
import edu.uci.ics.iskandea.service.idm.model.request.UserRequestModel;
import edu.uci.ics.iskandea.service.idm.model.response.UserNewResponseModel;
import edu.uci.ics.iskandea.service.idm.util.Param;
import edu.uci.ics.iskandea.service.idm.util.Util;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

@Path("new")
public class NewPage
{
    @Path("user")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(String jsonStream)
    {
        // New Model Mapping

        UserNewResponseModel responseModel = new UserNewResponseModel();

        UserRequestModel requestModel =
                Util.modelMapper(jsonStream, UserRequestModel.class, responseModel);

        if (requestModel == null)
            return responseModel.buildResponse();




        // New Query Handler

        String query =  "\n" +
                "SELECT CONCAT( '[', \n" +
	            "     GROUP_CONCAT( \n" +
                "        JSON_OBJECT( \n" +
                "         'userName', userName, \n" +
                "         'level', level, \n" +
                "         'status', status, \n" +
                "         'logIns', logIns)" +
                "     ), ']') as UserData \n" +
                "FROM users \n" +
                "WHERE level > ? \n" +
                "     AND status > ? \n" +
                "     AND logIns > ? \n" +
                "    AND userName LIKE ?; ";

        Param[] params = new Param[]{
            Param.create(Types.INTEGER, requestModel.getLevel()),
            Param.create(Types.INTEGER, requestModel.getStatus()),
            Param.create(Types.INTEGER, requestModel.getLogIns()),
            Param.create(Types.VARCHAR, '%' + requestModel.getUserName() + '%')};

        try {
            ResultSet rs = Util.prepareStatement(query, params).executeQuery();

            if (rs.next()) {
                UserData[] userData = Util.modelMapper(rs.getString("UserData"), UserData[].class);
                responseModel.setUserData(userData);

            } else {
                responseModel.setResult(Result.NO_USER_FOUND);

            }

        } catch (SQLException e) {
            return Util.serverError("SQLException was raised: " + e.getMessage());

        }

        return responseModel.buildResponse();
    }
}
