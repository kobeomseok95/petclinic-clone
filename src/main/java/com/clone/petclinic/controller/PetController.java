package com.clone.petclinic.controller;

import com.clone.petclinic.controller.dto.OwnerOneResponseDto;
import com.clone.petclinic.controller.dto.PetJoinAndEditDto;
import com.clone.petclinic.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/owners/{ownerId}")
public class PetController {

    private final PetService petService;

    @PostMapping("/pets/new")
    public OwnerOneResponseDto addPet(@RequestBody PetJoinAndEditDto request) {
        return petService.addPet(request);
    }

    @PutMapping("/pets/{petId}/edit")
    public OwnerOneResponseDto editPet(@PathVariable("ownerId") Long ownerId,
                                        @PathVariable("petId") Long petId,
                                        @RequestBody PetJoinAndEditDto request) {
        return petService.editPet(ownerId, petId, request);
    }
}
