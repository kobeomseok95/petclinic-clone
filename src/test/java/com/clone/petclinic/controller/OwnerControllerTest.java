package com.clone.petclinic.controller;

import com.clone.petclinic.controller.dto.*;
import com.clone.petclinic.domain.Owner;
import com.clone.petclinic.domain.base.Address;
import com.clone.petclinic.repository.OwnerRepository;
import com.clone.petclinic.service.OwnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(OwnerController.class)
class OwnerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OwnerService ownerService;

    Owner owner;

    @BeforeEach
    void setUp() throws Exception {
        owner = Owner.builder()
                .id(10L)
                .firstName("first")
                .lastName("last")
                .phone("01012341234")
                .address(
                        Address.builder()
                                .city("test")
                                .street("test")
                                .zipcode("test")
                                .build()
                )
                .build();
    }

    @Test
    void owner_등록() throws Exception{

        //given
        OwnerJoinAndEditRequestDto requestDto = createOwnerJoinRequestDto();
        OwnerOneResponseDto responseDto = createOwnerOneResponseDto(owner);
        when(ownerService.join(any(OwnerJoinAndEditRequestDto.class)))
                .thenReturn(owner.getId());
        when(ownerService.findOne(any(Long.class)))
                .thenReturn(responseDto);

        //when, then
        mockMvc.perform(post("/owners/new/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("10")))
                .andExpect(jsonPath("$.name", is("first last")))
                .andExpect(jsonPath("$.phone", is("01012341234")))
                .andExpect(jsonPath("$.city", is("test")))
                .andExpect(jsonPath("$.street", is("test")))
                .andExpect(jsonPath("$.zipcode", is("test")));
        verify(ownerService)
                .join(any(OwnerJoinAndEditRequestDto.class));
        verify(ownerService)
                .findOne(any(Long.class));
    }

    @Test
    void owner_수정() throws Exception {

        //given
        OwnerJoinAndEditRequestDto editRequest = createOwnerEditRequestDto();
        OwnerOneResponseDto editResponse = createOwnerEditResponseDto(editRequest);
        when(ownerService.findOne(any(Long.class)))
                .thenReturn(editResponse);

        //when, then
        mockMvc.perform(put("/owners/{id}", editRequest.getId())
                .content(objectMapper.writeValueAsString(editRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(editRequest.getId().toString())))
                .andExpect(jsonPath("$.name", is(editRequest.getFirstName() + " " + editRequest.getLastName())))
                .andExpect(jsonPath("$.phone", is(editRequest.getPhone())))
                .andExpect(jsonPath("$.city", is(editRequest.getCity())))
                .andExpect(jsonPath("$.street", is(editRequest.getStreet())))
                .andExpect(jsonPath("$.zipcode", is(editRequest.getZipcode())))
                .andDo(print());
        verify(ownerService)
                .edit(any(Long.class), any(OwnerJoinAndEditRequestDto.class));
        verify(ownerService)
                .findOne(any(Long.class));
    }

    @Test
    void owner_단건조회() throws Exception{

        //given
//        OwnerOneResponseDto dto = createOwnerOneResponseDto();
//        when(ownerService.findOne(any(Long.class)))
//                .thenReturn(dto);
//
//        //when, then
//        mockMvc.perform(get("/owners/3"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id", is("3")))
//                .andExpect(jsonPath("$.name", is("owner3")))
//                .andExpect(jsonPath("$.phone", is("test")))
//                .andExpect(jsonPath("$.city", is("test")))
//                .andExpect(jsonPath("$.street", is("test")))
//                .andExpect(jsonPath("$.zipcode", is("test")))
//                .andExpect(jsonPath("$.pets[0].id", is("4")))
//                .andExpect(jsonPath("$.pets[0].name", is("owner3pet1")))
//                .andExpect(jsonPath("$.pets[0].birth").exists())
//                .andExpect(jsonPath("$.pets[0].type", is("snake")))
//                .andExpect(jsonPath("$.pets[0].visits[0].visitDate").exists())
//                .andExpect(jsonPath("$.pets[0].visits[0].description", is("test")))
//                .andExpect(jsonPath("$.pets[0].visits[1].visitDate").exists())
//                .andExpect(jsonPath("$.pets[0].visits[1].description", is("test")))
//                .andExpect(jsonPath("$.pets[1].id", is("5")))
//                .andExpect(jsonPath("$.pets[1].name", is("owner3pet2")))
//                .andExpect(jsonPath("$.pets[1].birth").exists())
//                .andExpect(jsonPath("$.pets[1].type", is("dog")))
//                .andExpect(jsonPath("$.pets[1].visits[0].visitDate").exists())
//                .andExpect(jsonPath("$.pets[1].visits[0].description", is("test")))
//                .andExpect(jsonPath("$.pets[1].visits[1].visitDate").exists())
//                .andExpect(jsonPath("$.pets[1].visits[1].description", is("test")))
//                .andDo(print());
//        verify(ownerService, times(1))
//                .findOne(any(Long.class));
    }

    @Test
    void 여러_owners조회() throws Exception {

        //given
        List<OwnerMultipleResponseDto> dto = createOwnerMultipleResponseDto();
        when(ownerService.findByLastName(any()))
                .thenReturn(dto);

        //when, then
        mockMvc.perform(get("/owners")
                .param("lastName", "test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id", is("21")))
                .andExpect(jsonPath("$.[0].name", is("owner1")))
                .andExpect(jsonPath("$.[0].address", is("test1")))
                .andExpect(jsonPath("$.[0].city", is("test1")))
                .andExpect(jsonPath("$.[0].phone", is("test1")))
                .andExpect(jsonPath("$.[0].petNames[0]", is("owner1pet1")))
                .andExpect(jsonPath("$.[0].petNames[1]", is("owner1pet2")))
                .andExpect(jsonPath("$.[1].id", is("22")))
                .andExpect(jsonPath("$.[1].name", is("owner2")))
                .andExpect(jsonPath("$.[1].address", is("test2")))
                .andExpect(jsonPath("$.[1].city", is("test2")))
                .andExpect(jsonPath("$.[1].phone", is("test2")))
                .andExpect(jsonPath("$.[1].petNames[0]", is("owner2pet1")))
                .andExpect(jsonPath("$.[1].petNames[1]", is("owner2pet2")))
                .andExpect(jsonPath("$.[2].id", is("23")))
                .andExpect(jsonPath("$.[2].name", is("owner3")))
                .andExpect(jsonPath("$.[2].address", is("test3")))
                .andExpect(jsonPath("$.[2].city", is("test3")))
                .andExpect(jsonPath("$.[2].phone", is("test3")))
                .andExpect(jsonPath("$.[2].petNames[0]", is("owner3pet1")))
                .andExpect(jsonPath("$.[2].petNames[1]", is("owner3pet2")));
    }

    private OwnerOneResponseDto createOwnerEditResponseDto(OwnerJoinAndEditRequestDto editRequest) {
        return OwnerOneResponseDto.builder()
                .id(editRequest.getId().toString())
                .name(editRequest.getFirstName() + " " + editRequest.getLastName())
                .phone(editRequest.getPhone())
                .city(editRequest.getCity())
                .street(editRequest.getStreet())
                .zipcode(editRequest.getZipcode())
                .build();
    }

    private OwnerJoinAndEditRequestDto createOwnerEditRequestDto() {
        return OwnerJoinAndEditRequestDto.builder()
                .id(owner.getId())
                .firstName("edit")
                .lastName("edit")
                .phone("01011112222")
                .city("edit")
                .street("edit")
                .zipcode("edit")
                .build();
    }

    private List<OwnerMultipleResponseDto> createOwnerMultipleResponseDto() {
        return Arrays.asList(
                OwnerMultipleResponseDto.builder()
                        .id("21")
                        .name("owner1")
                        .address("test1")
                        .city("test1")
                        .phone("test1")
                        .petNames(
                                Arrays.asList("owner1pet1", "owner1pet2")
                        )
                        .build(),
                OwnerMultipleResponseDto.builder()
                        .id("22")
                        .name("owner2")
                        .address("test2")
                        .city("test2")
                        .phone("test2")
                        .petNames(
                                Arrays.asList("owner2pet1", "owner2pet2")
                        )
                        .build(),
                OwnerMultipleResponseDto.builder()
                        .id("23")
                        .name("owner3")
                        .address("test3")
                        .city("test3")
                        .phone("test3")
                        .petNames(
                                Arrays.asList("owner3pet1", "owner3pet2")
                        )
                        .build()
        );
    }

    private OwnerOneResponseDto createOwnerOneResponseDto(Owner owner) {
        return OwnerOneResponseDto.builder()
                .id(owner.getId().toString())
                .name(owner.getFirstName() + " " + owner.getLastName())
                .phone(owner.getPhone())
                .city(owner.getAddress().getCity())
                .street(owner.getAddress().getStreet())
                .zipcode(owner.getAddress().getZipcode())
                .build();
    }

    private List<OwnerPetsResponseDto> createOwnerPets() {
        return Arrays.asList(
                OwnerPetsResponseDto.builder()
                        .id("4")
                        .name("owner3pet1")
                        .birth(LocalDate.now().toString())
                        .type("snake")
                        .visits(createVisits())
                        .build(),
                OwnerPetsResponseDto.builder()
                        .id("5")
                        .name("owner3pet2")
                        .birth(LocalDate.now().toString())
                        .type("dog")
                        .visits(createVisits())
                        .build()
        );
    }

    private List<PetsVisitResponseDto> createVisits() {
        return Arrays.asList(
                PetsVisitResponseDto.builder()
                        .visitDate(LocalDate.now().toString())
                        .description("test")
                        .build(),
                PetsVisitResponseDto.builder()
                        .visitDate(LocalDate.now().toString())
                        .description("test")
                        .build()
        );
    }

    private OwnerJoinAndEditRequestDto createOwnerJoinRequestDto() {
        return OwnerJoinAndEditRequestDto.builder()
                .firstName("first")
                .lastName("last")
                .phone("01012341234")
                .city("test")
                .street("test")
                .zipcode("test")
                .build();
    }

}