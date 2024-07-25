package com.api.biblioteca.repository;

import com.api.biblioteca.entity.ClientPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientPostRepository extends JpaRepository<ClientPostEntity, Long> {
}
