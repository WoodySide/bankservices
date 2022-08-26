package com.woodyside.client.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.woodyside.client.model.audit.AuditModel;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;


@Entity(name = "Client")
@Table(name = "client", schema = "client_schema",
        indexes = {
        @Index(name = "uniqueMultiIndex", columnList = "CLIENT_NAME, CLIENT_LAST_NAME", unique = true),
        @Index(name = "uniqueEmailIndex", columnList = "CLIENT_EMAIL", unique = true)
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Client extends AuditModel implements Serializable {

    @Id
    @SequenceGenerator(
            allocationSize = 1,
            name = "client_id_sequence",
            sequenceName = "client_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "client_id_sequence")
    @Column(name = "CLIENT_ID")
    private Integer id;

    @Column(name = "IS_FRAUDSTER", columnDefinition = "boolean default true")
    private Boolean isFraudster;

    @Column(name = "CURRENT_SUM")
    @Max(value = 1_000_000, message = "Current sum should not be greater than 1_000_000")
    @Min(value = 0, message = "Current sum should not be greater than 100")
    private BigDecimal currentSum;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "firstName", column = @Column(name = "CLIENT_NAME")),
            @AttributeOverride(name = "lastName", column = @Column(name = "CLIENT_LAST_NAME")),
            @AttributeOverride(name = "email", column = @Column(name = "CLIENT_EMAIL", updatable = false, unique = true)),
            @AttributeOverride(name = "phone", column = @Column(name = "CLIENT_PHONE", updatable = false,unique = true))

    })
    private ClientData clientData;

    @OneToOne(cascade = CascadeType.ALL,
              fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "client_address_id")
    @JsonManagedReference
    private ClientAddress clientAddress;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Client client = (Client) o;
        return id != null && Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
