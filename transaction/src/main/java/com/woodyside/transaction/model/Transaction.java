package com.woodyside.transaction.model;

import com.woodyside.transaction.model.audit.AuditModel;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "Transaction")
@Table(name = "client_transaction", schema = "transaction_schema")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Transaction extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_transaction_id")
    private Integer id;

    @Column(name = "author")
    private String author;

    @Column(name = "transaction_status")
    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Column(name = "operation_kind")
    @Enumerated(value = EnumType.STRING)
    private TransactionKind transactionKind;

    @Column(name = "operation_sum")
    private BigDecimal operationSum;
}
