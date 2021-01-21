package com.clone.petclinic.controller.dto;

import com.clone.petclinic.domain.Owner;
import com.clone.petclinic.domain.Pet;
import com.clone.petclinic.domain.Visit;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class OwnerOneResponseDto {

    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;
    private String phone;
    private List<OwnersPetDto> pets;

    public OwnerOneResponseDto(Owner owner) {
        id = owner.getId();
        name = owner.getLastName() + " " + owner.getFirstName();
        city = owner.getAddress().getCity();
        street = owner.getAddress().getStreet();
        zipcode = owner.getAddress().getZipcode();
        phone = owner.getPhone();
        pets = owner.getPets().stream()
                .map(OwnersPetDto::new)
                .collect(Collectors.toList());
    }
}
