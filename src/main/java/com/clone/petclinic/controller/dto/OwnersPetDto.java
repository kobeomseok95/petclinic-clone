package com.clone.petclinic.controller.dto;

import com.clone.petclinic.domain.Pet;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnersPetDto {
    private String name;
    private String birth;
    private String type;
    private List<PetsVisitDto> visits;

    public OwnersPetDto(Pet pet){
        name = pet.getName();
        birth = pet.getDate().toString();
        type = pet.getPetType().getName();
        visits = pet.getVisits().stream()
                .map(PetsVisitDto::new)
                .collect(Collectors.toList());
    }
}
