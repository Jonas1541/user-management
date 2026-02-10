package com.jonasdurau.usermanagement.repositories;

import com.jonasdurau.usermanagement.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Método para o Search-as-you-type do HTMX
    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Validações de Unicidade
    boolean existsByDocument(String document);

    // Verifica se o documento existe em OUTRO usuário (para edição)
    boolean existsByDocumentAndIdNot(String document, Long id);
}