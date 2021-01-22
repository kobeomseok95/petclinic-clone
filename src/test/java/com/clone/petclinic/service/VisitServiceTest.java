package com.clone.petclinic.service;

import com.clone.petclinic.controller.dto.VisitViewDto;
import com.clone.petclinic.domain.Owner;
import com.clone.petclinic.domain.Pet;
import com.clone.petclinic.domain.PetType;
import com.clone.petclinic.domain.Visit;
import com.clone.petclinic.domain.base.Address;
import com.clone.petclinic.repository.PetRepository;
import com.clone.petclinic.repository.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Ref;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitServiceTest {

    @Mock
    PetRepository petRepository;
    @Mock
    VisitRepository visitRepository;
    @InjectMocks
    VisitService visitService;

    @Test
    void 방문날짜_addview() throws Exception {

        //given
        Pet mockPet = createPet();
        when(petRepository.findByPetIdWithOwner(any(Long.class)))
                .thenReturn(Optional.of(mockPet));

        //when
        VisitViewDto dto = visitService.addVisitView(mockPet.getId());

        //then
        verify(petRepository, times(1))
                .findByPetIdWithOwner(any(Long.class));
        assertAll(
                () -> assertEquals(dto.getOwnerId(), mockPet.getOwner().getId()),
                () -> assertEquals(dto.getOwnerName(), mockPet.getOwner().getFirstName() + " " + mockPet.getOwner().getLastName()),
                () -> assertEquals(dto.getPetId(), mockPet.getId()),
                () -> assertEquals(dto.getPetBirth(), mockPet.getDate().toString()),
                () -> assertEquals(dto.getPetName(), mockPet.getName()),
                () -> assertEquals(dto.getPetType(), mockPet.getPetType().getName()),
                () -> assertEquals(dto.getVisits().size(), 2)
        );
    }

    @Test
    void 방문일정생성() throws Exception{

        //given
        Pet mockPet = createPet();
        VisitViewDto request = requestAddVisitDto();
        when(petRepository.findById(request.getPetId()))
                .thenReturn(Optional.of(mockPet));

        //when
        Long ownerId = visitService.addVisit(request);

        //then
        verify(petRepository, times(1))
                .findById(request.getPetId());
        verify(visitRepository, times(1))
                .save(any(Visit.class));
        assertAll(
                () -> assertEquals(mockPet.getOwner().getId(), ownerId),
                () -> assertEquals(mockPet.getVisits().size(), 3)
        );
    }

    private VisitViewDto requestAddVisitDto() {
        return VisitViewDto.builder()
                .ownerId(1L)
                .ownerName("test")
                .petId(1L)
                .petName("pet1")
                .petBirth(LocalDate.now().toString())
                .petType(createPetType().getName())
                .description("test")
                .build();
    }

    private Pet createPet() {
        Pet pet = Pet.builder()
                .name("test")
                .petType(createPetType())
                .date(LocalDate.now())
                .visits(createVisit())
                .build();

        pet.addOwner(createOwner());
        for (Visit visit : pet.getVisits()) {
            visit.addPet(pet);
        }
        ReflectionTestUtils.setField(pet, "id", 1L);
        return pet;
    }

    private Set<Visit> createVisit(){
        return new HashSet<>(
                Arrays.asList(
                        Visit.builder().id(1L).date(LocalDate.now()).description("test").build(),
                        Visit.builder().id(2L).date(LocalDate.now()).description("test").build()
                )
        );
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
                .firstName("test")
                .lastName("test")
                .phone("test")
                .address(
                        Address.builder()
                                .city("test")
                                .street("test")
                                .zipcode("test")
                                .build())
                .pets(new HashSet<>())
                .build();
        ReflectionTestUtils.setField(owner, "id", 1L);
        return owner;
    }
}
















