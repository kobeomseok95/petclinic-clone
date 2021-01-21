package com.clone.petclinic.dummy;

import com.clone.petclinic.controller.dto.PetJoinAndEditRequestDto;
import com.clone.petclinic.domain.Pet;
import com.clone.petclinic.domain.PetType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PetDummy {

    public static Pet createPet(PetType petType) {
        return Pet.builder()
                .name("test")
                .date(LocalDate.now())
                .petType(petType)
                .build();
    }

    public static PetJoinAndEditRequestDto createPetJoinAndEditRequestDto() {
        return PetJoinAndEditRequestDto.builder()
                .name("edit")
                .birth("2021-01-01")
                .petType("snake")
                .build();
    }
}
