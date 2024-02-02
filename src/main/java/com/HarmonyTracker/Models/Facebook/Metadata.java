package com.HarmonyTracker.Models.Facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public  class Metadata {
    @JsonProperty("auth_type")
    private String authType;
    private String sso;
}