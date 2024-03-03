package com.HarmonyTracker.Entities.Enums;

public enum BodyFatMassClassificationType {
    LOW,
    EXCELLENT,
    GOOD,
    FAIR,
    POOR,
    HIGH,



    BodyFatMassClassificationType() {
    };
    //todo: should we add less age than 20?
    //source https://www.medicalnewstoday.com/articles/body-fat-percentage-chart#women
    public static BodyFatMassClassificationType getType(double percentage,GenderType genderType,int age) {
        if(genderType.equals(GenderType.male)){
            if(age>=20 && age<=29) {
                if (percentage < 8) {
                    return LOW;
                } else if (percentage >= 8 && percentage < 10.6 ) {
                    return EXCELLENT;
                } else if (percentage >= 10.6  && percentage < 14.9 ) {
                    return GOOD;
                } else if (percentage >= 14.9  && percentage < 18.7 ) {
                    return FAIR;
                } else if (percentage >= 18.7  && percentage < 23.2) {
                    return POOR;
                } else if (percentage >= 23.2) {
                    return HIGH;
                }
            }else if(age>=30 && age<=39){
                if (percentage < 8) {
                    return LOW;
                } else if (percentage >= 8 && percentage < 14.6 ) {
                    return EXCELLENT;
                } else if (percentage >= 14.6   && percentage < 18.3 ) {
                    return GOOD;
                } else if (percentage >= 18.3   && percentage < 21.4 ) {
                    return FAIR;
                } else if (percentage >= 21.4   && percentage < 25) {
                    return POOR;
                } else if (percentage >= 25) {
                    return HIGH;
                }
            }else if(age>=40 && age<=49){
                if (percentage < 8) {
                    return LOW;
                } else if (percentage >= 8 && percentage < 17.5 ) {
                    return EXCELLENT;
                } else if (percentage >= 17.5  && percentage < 20.7  ) {
                    return GOOD;
                } else if (percentage >= 20.7      && percentage < 23.5) {
                    return FAIR;
                } else if (percentage >= 23.5    && percentage < 26.7) {
                    return POOR;
                } else if (percentage >= 26.7) {
                    return HIGH;
                }
            }else if(age>=50 && age<=59){
                if (percentage < 8) {
                    return LOW;
                } else if (percentage >= 8 && percentage < 19.2) {
                    return EXCELLENT;
                } else if (percentage >= 19.2 && percentage < 22.2) {
                    return GOOD;
                } else if (percentage >= 22.2 && percentage < 24.7) {
                    return FAIR;
                } else if (percentage >= 24.7 && percentage < 27.9) {
                    return POOR;
                } else if (percentage >= 27.9) {
                    return HIGH;
                }
            }else if(age>=60 && age<=1000){
                if (percentage < 8) {
                    return LOW;
                } else if (percentage >= 8 && percentage < 19.8) {
                    return EXCELLENT;
                } else if (percentage >= 19.8  && percentage < 22.7) {
                    return GOOD;
                } else if (percentage >= 22.7  && percentage < 25.3) {
                    return FAIR;
                } else if (percentage >= 25.3  && percentage < 28.5) {
                    return POOR;
                } else if (percentage >= 28.5) {
                    return HIGH;
                }
            }
        }else if(genderType.equals(GenderType.female)){
            if(age>=20 && age<=29) {
                if (percentage < 14) {
                    return LOW;
                } else if (percentage >= 14 && percentage < 16.6) {
                    return EXCELLENT;
                } else if (percentage >= 16.6 && percentage < 19.5) {
                    return GOOD;
                } else if (percentage >= 19.5 && percentage < 22.8) {
                    return FAIR;
                } else if (percentage >= 22.8 && percentage < 27.2) {
                    return POOR;
                } else if (percentage >= 27.2) {
                    return HIGH;
                }
            }else if(age>=30 && age<=39){
                if (percentage < 14) {
                    return LOW;
                } else if (percentage >= 14 && percentage < 17.5) {
                    return EXCELLENT;
                } else if (percentage >= 17.5  && percentage < 20.9) {
                    return GOOD;
                } else if (percentage >= 20.9  && percentage < 24.7) {
                    return FAIR;
                } else if (percentage >= 24.7  && percentage < 29.2) {
                    return POOR;
                } else if (percentage >= 29.2) {
                    return HIGH;
                }
            }else if(age>=40 && age<=49){
                if (percentage < 14) {
                    return LOW;
                } else if (percentage >= 14 && percentage < 19.9) {
                    return EXCELLENT;
                } else if (percentage >= 19.9   && percentage < 23.9) {
                    return GOOD;
                } else if (percentage >= 23.9   && percentage < 27.7) {
                    return FAIR;
                } else if (percentage >= 27.7   && percentage < 31.9) {
                    return POOR;
                } else if (percentage >= 31.9) {
                    return HIGH;
                }
            }else if(age>=50 && age<=59){
                if (percentage < 14) {
                    return LOW;
                } else if (percentage >= 14 && percentage < 22.6 ) {
                    return EXCELLENT;
                } else if (percentage >= 22.6    && percentage < 27.1 ) {
                    return GOOD;
                } else if (percentage >= 27.1    && percentage < 30.5 ) {
                    return FAIR;
                } else if (percentage >= 30.5    && percentage < 34.6) {
                    return POOR;
                } else if (percentage >= 34.6) {
                    return HIGH;
                }
            }else if(age>=60 && age<=1000){
                if (percentage < 14) {
                    return LOW;
                } else if (percentage >= 14 && percentage < 23.3  ) {
                    return EXCELLENT;
                } else if (percentage >= 23.3 && percentage < 28 ) {
                    return GOOD;
                } else if (percentage >= 28 && percentage < 31.4 ) {
                    return FAIR;
                } else if (percentage >= 31.4 && percentage < 35.5) {
                    return POOR;
                } else if (percentage >= 35.5) {
                    return HIGH;
                }
            }
        }
        return null;
    }
}
