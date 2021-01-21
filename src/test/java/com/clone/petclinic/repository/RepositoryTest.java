package com.clone.petclinic.repository;

import com.clone.petclinic.controller.dto.OwnerJoinAndEditRequestDto;
import com.clone.petclinic.controller.dto.PetJoinAndEditRequestDto;
import com.clone.petclinic.domain.Owner;
import com.clone.petclinic.domain.Pet;
import com.clone.petclinic.domain.PetType;
import com.clone.petclinic.domain.base.Address;
import com.clone.petclinic.dummy.OwnerDummy;
import com.clone.petclinic.dummy.PetDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.security.cert.CollectionCertStoreParameters;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class RepositoryTest {

    private static Logger logger = LoggerFactory.getLogger(RepositoryTest.class);

    @Autowired
    EntityManager em;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    VisitRepository visitRepository;

    @Test
    void Owner등록() {

        //given
        Owner owner = OwnerDummy.createOwner();

        //when
        Owner save = ownerRepository.save(owner);

        //then
        assertNotNull(save.getId());
    }

    @Test
    void Owner가_여러명_있을_경우() throws Exception {

        //given
        for (int i = 0; i < 5; i++) {
            Owner owner = OwnerDummy.multipleCreateOwner(i);
            ownerRepository.save(owner);
        }

        //when
        Collection<Owner> owners = ownerRepository.findByLastName("ko");

        //then
        assertEquals(owners.size(), 5);
    }

    @Test
    void Owner가_한명_있을_경우() throws Exception {

        //given
        Owner owner = OwnerDummy.createOwner();
        ownerRepository.save(owner);

        //when
        Collection<Owner> findOwner = ownerRepository.findByLastName("o");

        //then
        assertEquals(findOwner.size(), 1);
    }

    @Test
    void Owner_수정() throws Exception {

        //given
        Owner owner = OwnerDummy.createOwner();
        Owner savedOwner = ownerRepository.save(owner);
        OwnerJoinAndEditRequestDto dto = OwnerDummy.createOwnerJoinAndEditRequestDto();

        //when
        owner.editOwner(dto);
        Owner editOwner = ownerRepository.findById(savedOwner.getId()).orElseThrow();

        assertAll(
                () -> assertEquals(savedOwner.getId(), editOwner.getId()),
                () -> assertEquals(editOwner.getFirstName(), "edit"),
                () -> assertEquals(editOwner.getLastName(), "edit"),
                () -> assertEquals(editOwner.getPhone(), "edit"),
                () -> assertEquals(editOwner.getAddress().getCity(), "edit"),
                () -> assertEquals(editOwner.getAddress().getStreet(), "edit"),
                () -> assertEquals(editOwner.getAddress().getZipcode(), "edit")
        );
    }

    @Test
    void Pet_등록() throws Exception {

        //given
        Owner owner = OwnerDummy.createOwner();
        ownerRepository.save(owner);
        Pet pet = PetDummy.createPet(petRepository.getPetTypes().get(0));
        pet.addOwner(owner);

        //when
        Pet savedPet = petRepository.save(pet);

        //then
        assertAll(
                () -> assertEquals(savedPet.getOwner().getId(), owner.getId()),
                () -> assertEquals(owner.getPets().size(), 1),
                () -> assertEquals(owner.getPets().iterator().next(), savedPet)
        );
    }


    @Test
    void Owner조회시_pet_pettype까지_한번에_가져와야한다() throws Exception {

        //given
        Owner owner = OwnerDummy.createOwner();
        ownerRepository.save(owner);

        Pet pet1 = PetDummy.createPet(petRepository.getPetTypes().get(0));
        pet1.addOwner(owner);
        Pet savedPet1 = petRepository.save(pet1);

        Pet pet2 = PetDummy.createPet(petRepository.getPetTypes().get(0));
        pet2.addOwner(owner);
        Pet savedPet2 = petRepository.save(pet2);

        em.flush();
        em.clear();
        //when
        logger.info("*********************************조회 쿼리 체크*********************************");
        Collection<Owner> findOwners = ownerRepository.findByLastName("ko");
        logger.info("*********************************조회 쿼리 이후*********************************");
        //then
        assertAll(
                () -> assertEquals(findOwners.size(), 1),
                () -> assertNotNull(findOwners.iterator().next().getPets()),
                () -> assertEquals(findOwners.iterator().next().getPets().size(), 2)
        );
    }

    @Test
    void Pet_수정() throws Exception{

        //given
        Owner owner = OwnerDummy.createOwner();
        ownerRepository.save(owner);
        Pet pet = PetDummy.createPet(petRepository.getPetTypes().get(0));
        pet.addOwner(owner);
        petRepository.save(pet);
        PetJoinAndEditRequestDto dto = PetDummy.createPetJoinAndEditRequestDto();
        PetType type = petRepository.findByPetTypeName(dto.getPetType());

        //when
        pet.editPet(dto, type);
        em.flush();

        //then
        Pet findPet = petRepository.findById(pet.getId()).orElseThrow();
        assertAll(
                () -> assertEquals(findPet.getOwner(), owner),
                () -> assertEquals(findPet.getPetType(), type),
                () -> assertEquals(owner.getPets().size(), 1),
                () -> assertEquals(owner.getPets().iterator().next(), findPet)
        );
    }
}















