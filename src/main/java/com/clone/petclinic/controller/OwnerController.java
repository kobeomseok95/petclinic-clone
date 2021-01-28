package com.clone.petclinic.controller;

import com.clone.petclinic.controller.dto.OwnerJoinAndEditRequestDto;
import com.clone.petclinic.controller.dto.OwnerMultipleResponseDto;
import com.clone.petclinic.controller.dto.OwnerOneResponseDto;
import com.clone.petclinic.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping("/new")
    public OwnerOneResponseDto join(@RequestBody OwnerJoinAndEditRequestDto dto) {
        Long ownerId = ownerService.join(dto);
        return ownerService.findOne(ownerId);
    }

    @PutMapping("/{id}")
    public OwnerOneResponseDto editOwner(@PathVariable("id") Long ownerId,
                                         @RequestBody OwnerJoinAndEditRequestDto request) {
        ownerService.edit(ownerId, request);
        return ownerService.findOne(ownerId);
    }

    @GetMapping("")
    public List<OwnerMultipleResponseDto> owners(@RequestParam(value = "lastName", required = false) String lastName){
        if (lastName == null) {
            lastName = "";
        }
        return ownerService.findByLastName(lastName);
    }

    @GetMapping("/{ownerId}")
    public OwnerOneResponseDto ownerOne(@PathVariable("ownerId") Long ownerId){
        return ownerService.findOne(ownerId);
    }
}
