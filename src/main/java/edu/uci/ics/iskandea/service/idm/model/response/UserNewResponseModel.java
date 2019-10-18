package edu.uci.ics.iskandea.service.idm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.iskandea.service.idm.base.ResponseModel;
import edu.uci.ics.iskandea.service.idm.base.Result;
import edu.uci.ics.iskandea.service.idm.model.data.UserData;

public class UserNewResponseModel extends ResponseModel
{
    @JsonProperty("userData")
    private UserData[] userData;

    public UserNewResponseModel()
    {
    }

    public UserData[] getUserData()
    {
        return userData;
    }

    public void setUserData(UserData[] userData)
    {
        if (userData == null) {
            this.setResult(Result.INTERNAL_SERVER_ERROR);

        } else if (userData.length == 0) {
            this.setResult(Result.NO_USER_FOUND);

        } else {
            this.setResult(Result.FOUND_USER);

        }

        this.userData = userData;
    }
}
