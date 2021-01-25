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

    public OwnerOneResponseDto addPet(PetJoinAndEditDto dto) {
        Owner owner = ownerRepository.findByIdFetch(Long.valueOf(dto.getOwnerId())).orElseThrow();

        Pet pet = new Pet();
        PetType type = petRepository.findByPetTypeName(dto.getPetType());
        pet.convertDtoIntoPet(dto, type);
        pet.addOwner(owner);
        petRepository.save(pet);

        return new OwnerOneResponseDto(owner);
    }

    @Transactional(readOnly = true)
    public PetJoinAndEditDto editPetView(Long id){
        Pet pet = petRepository.findByPetIdWithOwner(id).orElseThrow();

        PetJoinAndEditDto dto = new PetJoinAndEditDto();
        dto.convertPetIntoDto(pet);
        return dto;
    }

    public OwnerOneResponseDto editPet(Long ownerId, Long petId, PetJoinAndEditDto dto) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow();
        PetType type = petRepository.findByPetTypeName(dto.getPetType());

        pet.convertDtoIntoPet(dto, type);

        Owner owner = ownerRepository.findByIdFetch(ownerId).orElseThrow();
        return new OwnerOneResponseDto(owner);
    }
}
