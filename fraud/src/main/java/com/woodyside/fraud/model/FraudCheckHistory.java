package com.woodyside.fraud.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;


@Getter
@Setter(PRIVATE)
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FraudCheckHistory that = (FraudCheckHistory) o;
        return Objects.equals(id, that.id) && Objects.equals(clientUsername, that.clientUsername) && Objects.equals(isFraudster, that.isFraudster);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientUsername, isFraudster);
    }
}