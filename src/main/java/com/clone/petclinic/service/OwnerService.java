package com.clone.petclinic.service;

import com.clone.petclinic.controller.dto.OwnerJoinAndEditRequestDto;
import com.clone.petclinic.domain.Owner;
import com.clone.petclinic.domain.base.Address;
import com.clone.petclinic.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OwnerService {

    private final OwnerRepository ownerRepository;

    @Transactional
    public Long join(OwnerJoinAndEditRequestDto dto) {
        Owner owner = Owner.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .address(
                        Address.builder()
                                .city(dto.getCity())
                                .street(dto.getStreet())
                                .zipcode(dto.getZipcode())
                                .build()
                )
                .phone(dto.getPhone())
                .build();

        Owner saveOwner = ownerRepository.save(owner);
        return saveOwner.getId();
    }

    @Transactional
    public void edit(Long id, OwnerJoinAndEditRequestDto dto) {
        Owner owner = ownerRepository.findById(id).orElseThrow();
        owner.editOwner(dto);
    }

    public List<Owner> findAll() {
        return null;
    }

    public List<Owner> findByLastname() {
        return null;
    }
}
