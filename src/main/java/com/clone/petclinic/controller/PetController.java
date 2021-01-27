package com.clone.petclinic.controller;

import com.clone.petclinic.controller.dto.OwnerOneResponseDto;
import com.clone.petclinic.controller.dto.PetJoinAndEditDto;
import com.clone.petclinic.service.OwnerService;
import com.clone.petclinic.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/owners/{ownerId}")
public class PetController {

    private final PetService petService;
    private final OwnerService ownerService;

    @PostMapping("/pets/new")
    public OwnerOneResponseDto addPet(@PathVariable("ownerId") Long ownerId,
                                      @RequestBody PetJoinAndEditDto request) {
        petService.addPet(ownerId, request);
        return ownerService.findOne(ownerId);
    }

    @PutMapping("/pets/{petId}/edit")
    public OwnerOneResponseDto editPet(@PathVariable("ownerId") Long ownerId,
                                        @PathVariable("petId") Long petId,
                                        @RequestBody PetJoinAndEditDto request) {
        petService.editPet(ownerId, petId, request);
        return ownerService.findOne(ownerId);
    }
}
