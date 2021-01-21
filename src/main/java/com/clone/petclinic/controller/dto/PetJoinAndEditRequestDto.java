package com.clone.petclinic.controller.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PetJoinAndEditRequestDto {

    private String name;
    private String birth;
    private String petType;
}
