package com.clone.petclinic.dummy;

import com.clone.petclinic.controller.dto.PetJoinAndEditDto;
import com.clone.petclinic.domain.Pet;
import com.clone.petclinic.domain.PetType;

import java.time.LocalDate;
import java.util.HashSet;

public class PetDummy {

    public static Pet createPet(PetType petType) {
        return Pet.builder()
                .name("test")
                .date(LocalDate.now())
                .petType(petType)
                .visits(new HashSet<>())
                .build();
    }

    public static PetJoinAndEditDto createPetJoinAndEditRequestDto() {
        return PetJoinAndEditDto.builder()
                .petName("edit")
                .petBirth("2099-01-01")
                .petType("snake")
                .build();
    }
}
