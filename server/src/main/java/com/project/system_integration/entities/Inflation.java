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
    private Integer id;
    private Integer year;
    private Double value;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country role;
}
