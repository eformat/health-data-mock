package org.pophealth.health.data.mock.model;

import lombok.Data;

@Data
public class HealthData {

    private Integer steps;
    private Integer activeMinutes;
    private Integer age;
    private Double sleepRpms;
    private Double sleepHours;
    private Double sleepAvgRpms;
    private Double heartRateBpm;
}
