package com.insidegmail.developed.iot;

/**
 * Created by Admin on 16.05.2017.
 */

public class Automats {
    private String locationx;
    private String locationy;
    private String waterlevel;
    private String lastupdate;
    private String usercount;
    private String status;
    private String id;
    public Automats(String locationx, String locationy, String waterlevel, String lastupdate, String usercount, String status, String id) {
        this.locationx = locationx;
        this.locationy = locationy;
        this.waterlevel = waterlevel;
        this.lastupdate = lastupdate;
        this.usercount = usercount;
        this.status = status;
        this.id=id;
    }

    public String getLocationx() {
        return locationx;
    }

    public void setLocationx(String locationx) {
        this.locationx = locationx;
    }

    public String getLocationy() {
        return locationy;
    }

    public void setLocationy(String locationy) {
        this.locationy = locationy;
    }

    public String getWaterlevel() {
        return waterlevel;
    }

    public void setWaterlevel(String waterlevel) {
        this.waterlevel = waterlevel;
    }

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getUsercount() {
        return usercount;
    }

    public void setUsercount(String usercount) {
        this.usercount = usercount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}