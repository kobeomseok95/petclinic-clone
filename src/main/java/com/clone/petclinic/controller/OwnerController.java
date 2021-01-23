package com.clone.petclinic.controller;

import com.clone.petclinic.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OwnerController {

    private final OwnerService ownerService;


}
