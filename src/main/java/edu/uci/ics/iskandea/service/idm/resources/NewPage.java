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
    public Response getUsers(String jsonString)
    {
        // New Model Mapping

        UserNewResponseModel responseModel = new UserNewResponseModel();

        UserRequestModel requestModel =
                Util.modelMapper(jsonString, UserRequestModel.class, responseModel);

        if (requestModel == null)
            return responseModel.buildResponse();

        // New Query Handler
        String query =  "\n" +
                "SELECT JSON_ARRAYAGG(JSON_OBJECT( \n" +
                "         'email', email, \n" +
                "         'plevel', plevel, \n" +
                "         'status', status \n" +
                "   )) as UserData \n" +
                "FROM user \n" +
                "WHERE plevel > ? \n" +
                "     AND status > ? \n" +
                "     AND email LIKE ?; ";

        Param[] params = new Param[]{
            Param.create(Types.INTEGER, requestModel.getLevel()),
            Param.create(Types.INTEGER, requestModel.getStatus()),
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
