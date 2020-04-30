package com.alok.spring.batch.repository;

import com.alok.spring.batch.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
