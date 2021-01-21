package com.clone.petclinic.repository;

import com.clone.petclinic.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    // 객체 그래프로 탐색하기!, left 잊지말것, 해당 객체의 필드명으로 그래프 탐색
    @Query("select distinct o from Owner o" +
            " left join fetch o.pets p" +
            " left join fetch p.petType pt" +
            " where o.lastName like %:lastName%")
    @Transactional(readOnly = true)
    Collection<Owner> findByLastName(@Param("lastName") String lastName);
}
