package com.HarmonyTracker.Models.Apple;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AppleName {

    private String givenName;
    private String namePrefix;
    private String nameSuffix;
    private String middleName;
    private String nickname;
    private String familyName;

}
