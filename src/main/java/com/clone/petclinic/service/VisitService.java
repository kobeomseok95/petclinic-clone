package com.clone.petclinic.service;

import com.clone.petclinic.controller.dto.VisitViewDto;
import com.clone.petclinic.domain.Pet;
import com.clone.petclinic.domain.Visit;
import com.clone.petclinic.repository.PetRepository;
import com.clone.petclinic.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class VisitService {

    private final PetRepository petRepository;
    private final VisitRepository visitRepository;

    @Transactional(readOnly = true)
    public VisitViewDto addVisitView(Long petId){
        Pet findPet = petRepository.findByPetIdWithOwner(petId).orElseThrow();
        return new VisitViewDto(findPet);
    }

    public Long addVisit(VisitViewDto dto){
        Visit visit = new Visit();
        Pet pet = petRepository.findById(dto.getPetId()).orElseThrow();
        visit.convertDtoIntoVisit(dto, pet);
        visitRepository.save(visit);
        return dto.getOwnerId();
    }
}
