package com.platformbuilders.controleescolar.infra.repository;

import com.platformbuilders.controleescolar.domain.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
    Optional<School> findByCnpj(String cnpj);
    boolean existsByCnpj(String cnpj);
}