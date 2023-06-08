package com.project.system_integration.models;

import com.project.system_integration.entities.SocialExpense;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialExpenseDto {
    private Integer year;
    private Double value;
    private String unit;
    private String title;
    private String country;

    public SocialExpenseDto(SocialExpense expense) {
        this.year = expense.getYear();
        this.value = expense.getValue();
        this.title = expense.getUnit().getTitle();
        this.unit = expense.getUnit().getUnit();
        this.country = expense.getCountry().getName();
    }

}
