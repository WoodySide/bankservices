package com.woodyside.fraud.repository;

import com.woodyside.fraud.model.FraudCheckHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FraudRepository extends MongoRepository<FraudCheckHistory, String> {
}
