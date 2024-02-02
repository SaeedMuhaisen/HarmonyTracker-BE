package com.HarmonyTracker.Models.Facebook;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Error {

    private int code;
    private String message;
    private int subcode;
    private String type;
    private String fbtrace_id;

}