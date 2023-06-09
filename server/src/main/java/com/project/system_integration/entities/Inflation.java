package com.project.system_integration.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inflation")
public class Inflation {
    @Id
    @SequenceGenerator(
            name="inflation_id_sequence",
            sequenceName = "inflation_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "inflation_id_sequence"
    )
    private Integer id;
    private Integer year;
    private Double value;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    public Inflation(Integer year, Double value, Country country, Unit unit) {
        this.year = year;
        this.value = value;
        this.country = country;
        this.unit = unit;
    }
}
