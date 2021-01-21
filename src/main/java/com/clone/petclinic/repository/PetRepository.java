package com.clone.petclinic.repository;

import com.clone.petclinic.domain.Pet;
import com.clone.petclinic.domain.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("select pt from PetType pt")
    @Transactional(readOnly = true)
    List<PetType> getPetTypes();

    @Query("select pt from PetType pt where pt.name = :name")
    @Transactional(readOnly = true)
    PetType findByPetTypeName(@Param("name") String name);
}
