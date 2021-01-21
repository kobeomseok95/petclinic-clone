package com.clone.petclinic.service;

import com.clone.petclinic.controller.dto.OwnerJoinAndEditRequestDto;
import com.clone.petclinic.domain.Owner;
import com.clone.petclinic.domain.base.Address;
import com.clone.petclinic.repository.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

    @InjectMocks
    OwnerService ownerService;

    @Mock
    OwnerRepository ownerRepository;

    @Test
    void owner_등록() throws Exception {
        //given
        OwnerJoinAndEditRequestDto dto = getOwnerJoinRequestDto();
        Owner owner = getOwner(dto);
        given(ownerRepository.save(any()))
                .willReturn(owner);

        //when
        Long join = ownerService.join(dto);

        //then
        assertEquals(join, 10L);
    }

    @Test
    void owner_수정() throws Exception {
        //given
        OwnerJoinAndEditRequestDto dto = getOwnerJoinRequestDto();
        Owner target = getTargetOwner();
        given(ownerRepository.findById(any()))
                .willReturn(Optional.of(target));

        //when
        ownerService.edit(5L, dto);

        //then
        verify(ownerRepository, atLeastOnce()).findById(any());
        assertAll(
                () -> assertEquals(target.getId(), 5L),
                () -> assertEquals(target.getFirstName(), "test"),
                () -> assertEquals(target.getLastName(), "test"),
                () -> assertEquals(target.getPhone(), "test"),
                () -> assertEquals(target.getAddress().getCity(), "test"),
                () -> assertEquals(target.getAddress().getStreet(), "test"),
                () -> assertEquals(target.getAddress().getZipcode(), "test")
        );
    }

    @Test
    void owner_조회() throws Exception{
        //need
    }

    private Owner getTargetOwner() {
        Owner owner = Owner.builder()
                .firstName("before")
                .lastName("before")
                .phone("before")
                .address(
                        Address.builder()
                                .city("before")
                                .street("before")
                                .zipcode("before")
                                .build()
                )
                .build();

        ReflectionTestUtils.setField(owner, "id", 5L);
        return owner;
    }

    private Owner getOwner(OwnerJoinAndEditRequestDto dto) {
        Owner owner = Owner.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phone(dto.getPhone())
                .address(
                        Address.builder()
                                .city(dto.getCity())
                                .street(dto.getStreet())
                                .zipcode(dto.getZipcode())
                                .build()
                )
                .build();

        ReflectionTestUtils.setField(owner, "id", 10L);
        return owner;
    }

    private OwnerJoinAndEditRequestDto getOwnerJoinRequestDto() {
        return OwnerJoinAndEditRequestDto.builder()
                .firstName("test")
                .lastName("test")
                .city("test")
                .street("test")
                .zipcode("test")
                .phone("test")
                .build();
    }
}