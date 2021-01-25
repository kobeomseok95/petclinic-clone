package com.clone.petclinic.service;

import com.clone.petclinic.controller.dto.OwnerOneResponseDto;
import com.clone.petclinic.controller.dto.PetJoinAndEditDto;

public interface PetService {

    OwnerOneResponseDto addPet(PetJoinAndEditDto dto);

    PetJoinAndEditDto editPetView(Long id);

    OwnerOneResponseDto editPet(Long ownerId, Long petId, PetJoinAndEditDto dto);
}
