package com.clone.petclinic.service;

import com.clone.petclinic.controller.dto.AddVisitRequestDto;
import com.clone.petclinic.controller.dto.OwnerOneResponseDto;
import com.clone.petclinic.controller.dto.VisitResponseDto;

public interface VisitService {


    VisitResponseDto addVisitView(Long petId);
    OwnerOneResponseDto addVisit(Long ownerId, Long petId, AddVisitRequestDto request);
}
