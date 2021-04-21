package com.grokonez.springrestapi.onetoone.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grokonez.springrestapi.onetoone.model.Contact;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
	List<Contact> findByStudentId(Long studentId);
}
