package com.clone.petclinic.service;

import com.clone.petclinic.controller.dto.OwnerOneResponseDto;
import com.clone.petclinic.controller.dto.PetJoinAndEditDto;
import com.clone.petclinic.domain.Owner;
import com.clone.petclinic.domain.Pet;
import com.clone.petclinic.domain.PetType;
import com.clone.petclinic.domain.base.Address;
import com.clone.petclinic.repository.OwnerRepository;
import com.clone.petclinic.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @InjectMocks
    PetServiceImpl petService;

    @Test
    void Pet_등록() throws Exception {

        //given
        PetJoinAndEditDto requestDto = createPetJoinAndEditRequestDto();
        Owner owner = createOwner();
        PetType type = createPetType();
        when(ownerRepository.findByIdFetch(any(Long.class)))
                .thenReturn(Optional.of(owner));
        when(petRepository.findByPetTypeName(any(String.class)))
                .thenReturn(type);

        //when
        OwnerOneResponseDto responseDto = petService.addPet(requestDto);

        //then
        verify(ownerRepository, times(1))
                .findByIdFetch(any(Long.class));
        verify(petRepository, times(1))
                .findByPetTypeName(any(String.class));
        verify(petRepository, times(1))
                .save(any(Pet.class));

        assertEquals(responseDto.getId(), createOwner().getId().toString());
    }

    @Test
    void pet_수정화면() throws Exception {

        //given
        when(petRepository.findByPetIdWithOwner(any(Long.class)))
                .thenReturn((Optional.of(createPet())));

        //when
        PetJoinAndEditDto petJoinAndEditDto = petService.editPetView(1L);

        //then
        verify(petRepository, times(1))
                .findByPetIdWithOwner(any(Long.class));
        assertAll(
                () -> assertNotNull(petJoinAndEditDto),
                () -> assertEquals(petJoinAndEditDto.getPetId(), createPet().getId().toString()),
                () -> assertEquals(petJoinAndEditDto.getOwnerId(), createPet().getOwner().getId().toString())
        );
    }

    @Test
    void pet_수정() throws Exception {

        //given
        Pet testPet = createPet();
        PetJoinAndEditDto dto = createPetJoinAndEditRequestDto();
        dto.setPetId("1");
        Owner owner = createOwner();
        when(petRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(testPet));
        when(petRepository.findByPetTypeName(dto.getPetType()))
                .thenReturn(createPetTypeBird());
        when(ownerRepository.findByIdFetch(any(Long.class)))
                .thenReturn(Optional.of(owner));

        //when
        OwnerOneResponseDto responseDto = petService.editPet(Long.valueOf(dto.getOwnerId()),
                testPet.getId(), dto);

        //then
        verify(petRepository, times(1))
                .findById(Long.valueOf(dto.getPetId()));
        verify(petRepository, times(1))
                .findByPetTypeName(dto.getPetType());
        assertAll(
                () -> assertEquals(responseDto.getId(), testPet.getOwner().getId().toString()),
                () -> assertEquals(testPet.getPetType().getName(), "bird"),
                () -> assertEquals(testPet.getDate().toString(), "1995-09-22")
        );
    }

    private PetType createPetTypeBird() {
        PetType bird = PetType.builder()
                .name("bird")
                .build();
        ReflectionTestUtils.setField(bird, "id", 1L);
        return bird;
    }

    private Pet createPet() {
        Pet pet = Pet.builder()
                .name("test")
                .date(LocalDate.now())
                .petType(createPetType())
                .owner(createOwner())
                .build();

        ReflectionTestUtils.setField(pet, "id", 1L);
        return pet;
    }

    private PetJoinAndEditDto createPetJoinAndEditRequestDto() {
        return PetJoinAndEditDto.builder()
                .ownerId("1")
                .ownerName("test")
                .petBirth("1995-09-22")
                .petName("test")
                .petType("bird")
                .build();
    }

    private PetType createPetType() {
        PetType type = PetType.builder()
                .name("snake")
                .build();

        ReflectionTestUtils.setField(type, "id", 1L);
        return type;
    }

    private Owner createOwner() {
        Owner owner = Owner.builder()
                .pets(new HashSet<>())
                .address(
                        Address.builder()
                                .city("test")
                                .street("test")
                                .zipcode("test")
                                .build()
                )
                .firstName("test")
                .lastName("test")
                .phone("test")
                .build();
        ReflectionTestUtils.setField(owner, "id", 1L);
        return owner;
    }
}