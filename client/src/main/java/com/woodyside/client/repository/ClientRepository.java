package com.woodyside.client.repository;

import com.woodyside.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {

    Boolean existsByClientData_Email(String email);

    Optional<Client> findByClientData_Email(String email);
}
