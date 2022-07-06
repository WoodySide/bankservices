package com.woodyside.fraud.repository;

import com.woodyside.fraud.model.FraudCheckHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FraudRepository extends JpaRepository<FraudCheckHistory, Integer> {
}
