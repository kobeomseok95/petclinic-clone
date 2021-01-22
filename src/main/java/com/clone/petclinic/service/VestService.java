package com.clone.petclinic.service;

import com.clone.petclinic.repository.VetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class VestService {

    private final VetRepository vetRepository;
    

}
