package com.woodyside.client.repository;

import com.woodyside.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {

    @Query(value = "SELECT * FROM client_schema.client WHERE client_name=?1",nativeQuery = true)
    Optional<Client> findClientByFirstName(String firstName);

    Boolean existsByClientData_Email(String email);
}
