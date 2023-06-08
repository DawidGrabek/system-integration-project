package com.project.system_integration.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "socialExpenses")
public class SocialExpense {
    @Id
    private Integer id;
    private Integer year;
    private Double value;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;
}
