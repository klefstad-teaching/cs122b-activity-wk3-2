package edu.uci.ics.iskandea.service.idm.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequestModel
{
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("level")
    private int level;
    @JsonProperty("status")
    private int status;
    @JsonProperty("logIns")
    private int logIns;

    public UserRequestModel()
    {
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getLogIns()
    {
        return logIns;
    }

    public void setLogIns(int logIns)
    {
        this.logIns = logIns;
    }
}
