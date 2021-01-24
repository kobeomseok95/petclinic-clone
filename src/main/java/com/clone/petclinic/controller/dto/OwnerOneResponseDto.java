package com.clone.petclinic.controller.dto;

import com.clone.petclinic.domain.Owner;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerOneResponseDto {

    private String id;
    private String name;
    private String city;
    private String street;
    private String zipcode;
    private String phone;
    private List<OwnerPetsResponseDto> pets = new ArrayList<>();

    public OwnerOneResponseDto(Owner owner) {
        id = owner.getId().toString();
        name = owner.getLastName() + " " + owner.getFirstName();
        city = owner.getAddress().getCity();
        street = owner.getAddress().getStreet();
        zipcode = owner.getAddress().getZipcode();
        phone = owner.getPhone();
        if (owner.getPets() != null) {
            pets = owner.getPets().stream()
                    .map(OwnerPetsResponseDto::new)
                    .collect(Collectors.toList());
        }
    }
}
