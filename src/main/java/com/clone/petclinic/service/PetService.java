package com.clone.petclinic.service;


import com.clone.petclinic.controller.dto.PetJoinAndEditDto;
import com.clone.petclinic.domain.Owner;
import com.clone.petclinic.domain.Pet;
import com.clone.petclinic.domain.PetType;
import com.clone.petclinic.repository.OwnerRepository;
import com.clone.petclinic.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class PetService {

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;

    public Long addPet(PetJoinAndEditDto dto) {
        Owner owner = ownerRepository.findById(dto.getOwnerId()).orElseThrow();

        Pet pet = new Pet();
        PetType type = petRepository.findByPetTypeName(dto.getPetType());
        pet.convertDtoIntoPet(dto, type);
        pet.addOwner(owner);

        petRepository.save(pet);
        return owner.getId();
    }

    @Transactional(readOnly = true)
    public PetJoinAndEditDto editPetView(Long id){
        Pet pet = petRepository.findByPetIdWithOwner(id).orElseThrow();

        PetJoinAndEditDto dto = new PetJoinAndEditDto();
        dto.convertPetIntoDto(pet);
        return dto;
    }

    public Long editPet(PetJoinAndEditDto dto) {
        Pet pet = petRepository.findById(dto.getPetId()).orElseThrow();
        PetType type = petRepository.findByPetTypeName(dto.getPetType());

        pet.convertDtoIntoPet(dto, type);

        return pet.getOwner().getId();
    }
}
