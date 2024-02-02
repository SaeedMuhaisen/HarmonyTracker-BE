package com.HarmonyTracker.Models.Facebook;
import java.util.List;
public class FBAccessTokenJSON {


    private String accessToken;
    private List<String> permissions;
    private List<String> declinedPermissions;
    private List<String> expiredPermissions;
    private String applicationID;
    private String userID;
    private long expirationTime;
    private double lastRefreshTime;
    private long dataAccessExpirationTime;

    // Getters and Setters

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<String> getDeclinedPermissions() {
        return declinedPermissions;
    }

    public void setDeclinedPermissions(List<String> declinedPermissions) {
        this.declinedPermissions = declinedPermissions;
    }

    public List<String> getExpiredPermissions() {
        return expiredPermissions;
    }

    public void setExpiredPermissions(List<String> expiredPermissions) {
        this.expiredPermissions = expiredPermissions;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public double getLastRefreshTime() {
        return lastRefreshTime;
    }

    public void setLastRefreshTime(double lastRefreshTime) {
        this.lastRefreshTime = lastRefreshTime;
    }

    public long getDataAccessExpirationTime() {
        return dataAccessExpirationTime;
    }

    public void setDataAccessExpirationTime(long dataAccessExpirationTime) {
        this.dataAccessExpirationTime = dataAccessExpirationTime;
    }

}
