package com.jonasdurau.usermanagement.services;

import com.jonasdurau.usermanagement.dtos.UserDTO;
import com.jonasdurau.usermanagement.entities.Address;
import com.jonasdurau.usermanagement.entities.User;
import com.jonasdurau.usermanagement.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<User> search(String query, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(query, pageable);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    @Transactional
    public User save(UserDTO dto) {
        if (repository.existsByDocument(dto.getDocument())) {
            throw new IllegalArgumentException("Document already registered: " + dto.getDocument());
        }
        return repository.save(toEntity(dto));
    }

    @Transactional
    public User update(Long id, UserDTO dto) {
        User existingUser = findById(id);

        if (repository.existsByDocumentAndIdNot(dto.getDocument(), id)) {
            throw new IllegalArgumentException("Document already registered to another user");
        }

        existingUser.setName(dto.getName());
        existingUser.setBirthDate(dto.getBirthDate());
        existingUser.setDocument(dto.getDocument());

        Address newAddress = new Address(
                dto.getAddressLine(),
                dto.getAddressNumber(),
                dto.getCity(),
                dto.getState(),
                dto.getZip());
        existingUser.setAddress(newAddress);

        return repository.save(existingUser);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private User toEntity(UserDTO dto) {
        Address address = new Address(
            dto.getAddressLine(),
            dto.getAddressNumber(),
            dto.getCity(),
            dto.getState(),
            dto.getZip()
        );

        User user = new User(
            dto.getName(),
            dto.getBirthDate(),
            dto.getDocument(),
            address
        );
        
        return user;
    }
}