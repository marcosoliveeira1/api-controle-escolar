package com.platformbuilders.controleescolar.student.infra.repository;

import com.platformbuilders.controleescolar.student.domain.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}