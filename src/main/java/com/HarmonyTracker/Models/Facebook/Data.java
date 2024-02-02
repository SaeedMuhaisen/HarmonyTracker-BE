package com.HarmonyTracker.Models.Facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@lombok.Data
@Getter
@Setter
public class Data {

    @JsonProperty("app_id")
    private String appId;

    private String type;
    private String application;

    @JsonProperty("data_access_expires_at")
    private long dataAccessExpiresAt;

    private java.lang.Error error;

    @JsonProperty("expires_at")
    private long expiresAt;
    @JsonProperty("issued_at")
    private long issuedAt;
    @JsonProperty("is_valid")
    private boolean isValid;

    private Metadata metadata;
    private List<String> scopes;

    @JsonProperty("user_id")
    private String userId;

}