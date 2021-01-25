package com.clone.petclinic.controller;

import com.clone.petclinic.controller.dto.OwnerOneResponseDto;
import com.clone.petclinic.controller.dto.OwnerPetsResponseDto;
import com.clone.petclinic.controller.dto.PetJoinAndEditDto;
import com.clone.petclinic.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
class PetControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PetService petService;

    @Test
    void pet_등록() throws Exception{

        //given
        PetJoinAndEditDto request = createPetJoinDto();
        OwnerOneResponseDto response = createOneResponseDto(request);
        when(petService.addPet(any(PetJoinAndEditDto.class)))
                .thenReturn(response);

        //when, then
        mockMvc.perform(post("/owners/{id}/pets/new", response.getId())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("3")))
                .andExpect(jsonPath("$.name", is("owner3")))
                .andExpect(jsonPath("$.phone", is("test")))
                .andExpect(jsonPath("$.city", is("test")))
                .andExpect(jsonPath("$.street", is("test")))
                .andExpect(jsonPath("$.zipcode", is("test")))
                .andExpect(jsonPath("$.pets[0].id", is("1")))
                .andExpect(jsonPath("$.pets[0].name", is("pet1")))
                .andExpect(jsonPath("$.pets[0].birth").exists())
                .andExpect(jsonPath("$.pets[0].type", is("snake")));
    }

    @Test
    void pet_수정() throws Exception{

        //given
        PetJoinAndEditDto requestDto = createPetEditRequestDto();
        OwnerOneResponseDto responseDto = createOwnerOneResponseDtoForPetEdit(requestDto);
        when(petService.editPet(any(Long.class), any(Long.class), any(PetJoinAndEditDto.class)))
                .thenReturn(responseDto);

        //when, then
        mockMvc.perform(put("/owners/{ownerId}/pets/{petId}/edit", 1L, 1L)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("owner1")))
                .andExpect(jsonPath("$.pets.length()", is(1)))
                .andExpect(jsonPath("$.pets[0].id", is("1")))
                .andExpect(jsonPath("$.pets[0].birth").exists())
                .andExpect(jsonPath("$.pets[0].name", is("edit")))
                .andExpect(jsonPath("$.pets[0].type", is("bird")))
                .andDo(print());
    }

    private OwnerOneResponseDto createOwnerOneResponseDtoForPetEdit(PetJoinAndEditDto request) {
        return OwnerOneResponseDto.builder()
                .id(request.getOwnerId())
                .name(request.getOwnerName())
                .pets(
                        Arrays.asList(
                                OwnerPetsResponseDto.builder()
                                        .id(request.getPetId())
                                        .birth(request.getPetBirth())
                                        .name(request.getPetName())
                                        .type(request.getPetType())
                                        .build()
                        )
                )
                .build();
    }

    private PetJoinAndEditDto createPetEditRequestDto() {
        return PetJoinAndEditDto.builder()
                .ownerId("1")
                .ownerName("owner1")
                .petId("1")
                .petName("edit")
                .petType("bird")
                .petBirth(LocalDate.now().toString())
                .build();
    }

    private PetJoinAndEditDto createPetJoinDto() {
        return PetJoinAndEditDto.builder()
                .petBirth(LocalDate.now().toString())
                .petName("pet1")
                .petType("snake")
                .build();
    }

    private OwnerOneResponseDto createOneResponseDto(PetJoinAndEditDto request) {
        return OwnerOneResponseDto.builder()
                .id("3")
                .name("owner3")
                .phone("test")
                .city("test")
                .street("test")
                .zipcode("test")
                .pets(Arrays.asList(
                        OwnerPetsResponseDto.builder()
                                .id("1")
                                .type(request.getPetType())
                                .name(request.getPetName())
                                .birth(request.getPetBirth())
                                .build()))
                .build();
    }
}



















