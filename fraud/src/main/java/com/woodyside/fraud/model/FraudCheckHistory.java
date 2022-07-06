package com.woodyside.fraud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "FraudCheckHistory")
@Table(name = "fraud_check_history")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FraudCheckHistory {

    @Id
    @SequenceGenerator(
            allocationSize = 1,
            name = "fraud_check_history_sequence",
            sequenceName = "fraud_check_history_sequence"
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "fraud_check_history_sequence")
    private Integer id;

    @Column(name = "client_username")
    private String clientUsername;

    @Column(name = "is_fraudster")
    private Boolean isFraudster;
}