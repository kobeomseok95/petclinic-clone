package com.clone.petclinic.controller.dto;

import lombok.*;
import org.hibernate.annotations.BatchSize;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddVisitRequestDto {

    private String petId;
    private String date;
    private String description;
}
