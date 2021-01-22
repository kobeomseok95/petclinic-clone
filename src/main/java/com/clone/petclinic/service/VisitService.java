package com.clone.petclinic.service;

import com.clone.petclinic.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class VisitService {

    private final VisitRepository visitRepository;

//    public Long addVisit(AddVisitRequestDto dto){
//
//    }
}
