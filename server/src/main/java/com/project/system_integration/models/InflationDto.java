package com.project.system_integration.models;

import com.project.system_integration.entities.Inflation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InflationDto {
    private Integer year;
    private Double value;
    private String title;
    private String unit;
    private String country;

    public InflationDto(Inflation inflation) {
        this.year = inflation.getYear();
        this.value = inflation.getValue();
        this.title = inflation.getUnit().getTitle();
        this.unit = inflation.getUnit().getUnit();
        this.country = inflation.getCountry().getName();
    }
}
