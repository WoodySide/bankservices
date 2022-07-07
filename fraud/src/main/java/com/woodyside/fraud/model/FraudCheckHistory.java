package com.woodyside.fraud.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@Builder
@Document(value = "fraud_check_auth_history")
@NoArgsConstructor
@AllArgsConstructor
public class FraudCheckHistory {

    @Id
    @Field(value = "ID")
    private String id;

    @Field(name = "client_username")
    @Indexed(unique = true)
    private String clientUsername;

    @Field(name = "is_fraudster")
    private Boolean isFraudster;
}