package com.grokonez.springrestapi.onetoone.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grokonez.springrestapi.onetoone.model.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}