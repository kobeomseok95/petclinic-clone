package com.clone.petclinic.controller.dto;

import com.clone.petclinic.domain.Visit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetsVisitDto {
    private String visitDate;
    private String description;

    public PetsVisitDto(Visit visit){
        this.visitDate = visit.getDate().toString();
        description = visit.getDescription();
    }
}
