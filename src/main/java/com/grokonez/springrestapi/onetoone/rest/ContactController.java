package com.grokonez.springrestapi.onetoone.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grokonez.springrestapi.onetoone.exception.NotFoundException;
import com.grokonez.springrestapi.onetoone.jpa.ContactRepository;
import com.grokonez.springrestapi.onetoone.jpa.StudentRepository;
import com.grokonez.springrestapi.onetoone.model.Contact;

@RestController
@RequestMapping("/api")
public class ContactController {
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@GetMapping("/contacts")
	public List<Contact> getAllContacts(Contact contact){
		return contactRepository.findAll();
	}
	
    @GetMapping("/students/{studentId}/contacts")
    public Contact getContactByStudentId(@PathVariable Long studentId) {
    	
        if(!studentRepository.existsById(studentId)) {
            throw new NotFoundException("Student not found!");
        }
    	
    	List<Contact> contacts = contactRepository.findByStudentId(studentId);
    	if(contacts.size() > 0) {
    		return contacts.get(0);
    	}else {
    		throw new NotFoundException("Contact not found!");
    	}
    }
    
    @PostMapping("/students/{studentId}/contacts")
    public Contact addContact(@PathVariable Long studentId,
                            Contact contact) {
        return studentRepository.findById(studentId)
                .map(student -> {
                    contact.setStudent(student);
                    return contactRepository.save(contact);
                }).orElseThrow(() -> new NotFoundException("Student not found!"));
    }
    
    @PutMapping("/contacts/{contactId}")
    public Contact updateContact(@PathVariable Long contactId,
                               @Valid @RequestBody Contact contactUpdated) {
        return contactRepository.findById(contactId)
                .map(contact -> {
                    contact.setStudent(contactUpdated.getStudent());
                    contact.setAddress(contactUpdated.getAddress());
                    contact.setOrderDetails(contactUpdated.getOrderDetails());
                    contact.setPrice(contactUpdated.getPrice());
                    return contactRepository.save(contact);
                }).orElseThrow(() -> new NotFoundException("Contact not found!"));
    }
    
    @DeleteMapping("/contacts/{contactId}")
    public String deleteContact(@PathVariable Long contactId) {
        return contactRepository.findById(contactId)
                .map(contact -> {
                    contactRepository.delete(contact);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new NotFoundException("Contact not found!"));
    }
}
