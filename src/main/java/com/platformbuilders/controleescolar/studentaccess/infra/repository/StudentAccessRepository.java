package com.platformbuilders.controleescolar.studentaccess.infra.repository;

import com.platformbuilders.controleescolar.studentaccess.domain.model.StudentAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentAccessRepository extends JpaRepository<StudentAccess, Long> {
    Optional<StudentAccess> findByStudentId(Long studentId);
}