package com.clone.petclinic.controller.dto;

import com.clone.petclinic.domain.Pet;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PetJoinAndEditDto {

    private String ownerId;
    private String ownerName;
    private String petId;
    private String petName;
    private String petBirth;
    private String petType;

    public void convertPetIntoDto(Pet pet){
        this.ownerId = pet.getOwner().getId().toString();
        this.ownerName = pet.getOwner().getFirstName() + " " + pet.getOwner().getLastName();
        this.petId = pet.getId().toString();
        this.petName = pet.getName();
        this.petBirth = pet.getDate().toString();
        this.petType = pet.getPetType().getName();
    }
}
