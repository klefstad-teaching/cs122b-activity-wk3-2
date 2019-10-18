package edu.uci.ics.iskandea.service.idm.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.iskandea.service.idm.model.data.UserData;

public class UserStandardResponseModel
{
    @JsonProperty("resultCode")
    private Integer resultCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("userData")
    private UserData[] userData;

    public UserStandardResponseModel()
    {
    }

    public int getResultCode()
    {
        return resultCode;
    }

    public void setResultCode(int resultCode)
    {
        this.resultCode = resultCode;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public UserData[] getUserData()
    {
        return userData;
    }

    public void setUserData(UserData[] userData)
    {
        this.userData = userData;
    }
}
