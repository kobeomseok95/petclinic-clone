package com.clone.petclinic.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OwnerJoinAndEditRequestDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String city;
    private String street;
    private String zipcode;


}