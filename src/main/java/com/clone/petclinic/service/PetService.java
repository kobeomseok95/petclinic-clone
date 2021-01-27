package com.clone.petclinic.service;

import com.clone.petclinic.controller.dto.OwnerOneResponseDto;
import com.clone.petclinic.controller.dto.PetJoinAndEditDto;

public interface PetService {

    void addPet(Long ownerId, PetJoinAndEditDto dto);

    PetJoinAndEditDto editPetView(Long id);

    void editPet(Long ownerId, Long petId, PetJoinAndEditDto dto);
}
