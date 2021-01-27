package com.clone.petclinic.service;


import com.clone.petclinic.controller.dto.OwnerOneResponseDto;
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
public class PetServiceImpl implements PetService {

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;

    public void addPet(Long ownerId, PetJoinAndEditDto dto) {
        Owner owner = ownerRepository.findByIdFetch(ownerId).orElseThrow();
        PetType type = petRepository.findByPetTypeName(dto.getPetType());

        Pet pet = new Pet(owner, type, dto);
        petRepository.save(pet);
    }

    @Transactional(readOnly = true)
    public PetJoinAndEditDto editPetView(Long id){
        Pet pet = petRepository.findByPetIdWithOwner(id).orElseThrow();

        PetJoinAndEditDto dto = new PetJoinAndEditDto();
        dto.convertPetIntoDto(pet);
        return dto;
    }

    public void editPet(Long ownerId, Long petId, PetJoinAndEditDto dto) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow();
        PetType type = petRepository.findByPetTypeName(dto.getPetType());

        pet.convertDtoIntoPet(dto, type);
    }
}
