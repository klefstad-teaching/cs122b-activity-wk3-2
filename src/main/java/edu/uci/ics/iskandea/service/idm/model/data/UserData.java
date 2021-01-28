package edu.uci.ics.iskandea.service.idm.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserData
{
    @JsonProperty("email")
    private String userName;
    @JsonProperty("plevel")
    private int level;
    @JsonProperty("status")
    private int status;

    public UserData()
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

}
