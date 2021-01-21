package com.clone.petclinic.service;

import com.clone.petclinic.controller.dto.*;
import com.clone.petclinic.domain.Owner;
import com.clone.petclinic.domain.Pet;
import com.clone.petclinic.domain.PetType;
import com.clone.petclinic.domain.Visit;
import com.clone.petclinic.domain.base.Address;
import com.clone.petclinic.dummy.OwnerDummy;
import com.clone.petclinic.repository.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.*;

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
        OwnerJoinAndEditRequestDto dto = OwnerDummy.createOwnerJoinAndEditRequestDto();
        when(ownerRepository.save(any(Owner.class)))
                .thenReturn(getOwner(dto));

        //when
        Long join = ownerService.join(dto);

        //then
        verify(ownerRepository, atLeastOnce()).save(any(Owner.class));
        assertEquals(join, 10L);
    }

    @Test
    void owner_수정() throws Exception {
        //given
        OwnerJoinAndEditRequestDto dto = getOwnerJoinRequestDto();
        Owner target = getTargetOwner();
        dto.setId(5L);
        when(ownerRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(target));

        //when
        ownerService.edit(dto);

        //then
        verify(ownerRepository, atLeastOnce()).findById(any(Long.class));
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
    void owner_단건_조회() throws Exception {
        
        //given
        when(ownerRepository.findByIdFetch(any(Long.class)))
                .thenReturn(Optional.of(getOwnerDetail()));
        
        //when
        OwnerOneResponseDto one = ownerService.findOne(20L);
        
        //then
        verify(ownerRepository, atLeastOnce()).findByIdFetch(any(Long.class));
        assertEquals(one.getPets().size(), 2);

        for (OwnersPetDto pet : one.getPets()) {
            assertEquals(pet.getVisits().size(), 2);
            for (PetsVisitDto visit : pet.getVisits()) {
                assertNotNull(visit);
            }
        }
    }

    @Test
    void owner_다수_조회() throws Exception{

        //given
        when(ownerRepository.findByLastName(any(String.class)))
                .thenReturn(getOwners());

        //when
        List<OwnerMultipleResponseDto> dtos = ownerService.findByLastName("owner1");

        //then
        verify(ownerRepository, atLeastOnce()).findByLastName("owner1");
        assertEquals(dtos.size(), 3);
        for (OwnerMultipleResponseDto dto : dtos) {
            assertNotNull(dto);
            assertEquals(dto.getPetNames().size(), 2);
        }
    }

    private Collection<Owner> getOwners() {
        return Arrays.asList(
                Owner.builder().id(1L).firstName("owner1").lastName("owner1").phone("test1")
                        .address(Address.builder().city("owner1").street("owner1").zipcode("owner1").build())
                        .pets(createPets()).build(),
                Owner.builder().id(1L).firstName("owner1").lastName("owner1").phone("test1")
                        .address(Address.builder().city("owner1").street("owner1").zipcode("owner1").build())
                        .pets(createPets()).build(),
                Owner.builder().id(1L).firstName("owner1").lastName("owner1").phone("test1")
                        .address(Address.builder().city("owner1").street("owner1").zipcode("owner1").build())
                        .pets(createPets()).build()
        );
    }

    private Set<Pet> createPets() {
        Set<Pet> pets = new HashSet<>();
        pets.add(
            Pet.builder().id(2L).name("pet1").date(LocalDate.now()).build()
        );
        pets.add(
                Pet.builder().id(3L).name("pet2").date(LocalDate.now()).build()
        );
        return pets;
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
    
    private Owner getOwnerDetail() {
        Owner owner = Owner.builder()
                .firstName("test")
                .lastName("test")
                .phone("test")
                .address(
                        Address.builder()
                                .city("test")
                                .street("test")
                                .zipcode("test")
                                .build()
                )
                .pets(getPets())
                .build();
        ReflectionTestUtils.setField(owner, "id", 1L);
        return owner;
    }

    private Set<Pet> getPets() {
        Set<Pet> pets = new HashSet<>();
        for (int i = 1; i < 3; i++) {
            pets.add(createPet(i));
        }
        return pets;
    }

    private Pet createPet(int i){
        Pet pet = Pet.builder()
                .name("test")
                .date(LocalDate.now())
                .petType(
                        PetType.builder()
                                .name("lizard")
                                .build())
                .visits(getVisits())
                .build();
        ReflectionTestUtils.setField(pet, "id", Integer.toUnsignedLong(i));

        return pet;
    }

    private Set<Visit> getVisits() {
        Set<Visit> visits = new HashSet<>();
        for (int i = 1; i < 3; i++) {
            visits.add(createVisit(i));
        }
        return visits;
    }

    private Visit createVisit(int i) {
        Visit visit = Visit.builder()
                .description("test")
                .date(LocalDate.now())
                .build();

        ReflectionTestUtils.setField(visit, "id", Integer.toUnsignedLong(i));
        return visit;
    }
}