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
        return OwnerOneResponseDto.builder()
                .id(ownerId.toString())
                .name(dto.getFirstName() + " " + dto.getLastName())
                .city(dto.getCity())
                .street(dto.getStreet())
                .zipcode(dto.getZipcode())
                .phone(dto.getPhone())
                .build();
    }

    @GetMapping("")
    public List<OwnerMultipleResponseDto> owners(@RequestParam(value = "lastName", required = false) String lastName){
        if (lastName == null) {
            lastName = "";
        }
        return ownerService.findByLastName(lastName);
    }

    @GetMapping("/{id}")
    public OwnerOneResponseDto ownerOne(@PathVariable("id") Long ownerId){
        return ownerService.findOne(ownerId);
    }

    @PutMapping("/{id}")
    public OwnerOneResponseDto editOwner(@PathVariable("id") Long ownerId,
                                         @RequestBody OwnerJoinAndEditRequestDto request) {
        return ownerService.edit(ownerId, request);
    }
}
