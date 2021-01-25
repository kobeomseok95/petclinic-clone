package com.clone.petclinic.controller;

import com.clone.petclinic.controller.dto.AddVisitRequestDto;
import com.clone.petclinic.controller.dto.OwnerOneResponseDto;
import com.clone.petclinic.controller.dto.VisitResponseDto;
import com.clone.petclinic.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/owners/{ownerId}/pets/{petId}")
public class VisitController {

    private final VisitService visitService;

    @PostMapping("/visits/new")
    public OwnerOneResponseDto createVisit(@PathVariable("ownerId") Long ownerId,
                                        @PathVariable("petId") Long petId,
                                        @RequestBody AddVisitRequestDto request) {
        return visitService.addVisit(ownerId, petId, request);
    }
}
