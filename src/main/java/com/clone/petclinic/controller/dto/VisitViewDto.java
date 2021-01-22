package com.clone.petclinic.controller.dto;

import com.clone.petclinic.domain.Pet;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class VisitViewDto {

    private Long ownerId;
    private String ownerName;

    private Long petId;
    private String petName;
    private String petBirth;
    private String petType;
    private String description;

    private List<PetsVisitDto> visits;

    public VisitViewDto(Pet pet) {
        ownerId = pet.getOwner().getId();
        ownerName = pet.getOwner().getFirstName() + " " + pet.getOwner().getLastName();
        petId = pet.getId();
        petName = pet.getName();
        petBirth = pet.getDate().toString();
        petType = pet.getPetType().getName();
        visits = pet.getVisits().stream()
                .map(PetsVisitDto::new)
                .collect(Collectors.toList());
    }
}
