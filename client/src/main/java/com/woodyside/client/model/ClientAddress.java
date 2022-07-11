package com.woodyside.client.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Entity(name = "ClientAddress")
@Table(name = "client_address", schema = "client_schema")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "client")
public class ClientAddress implements Serializable {

    @Id
    @SequenceGenerator(
            allocationSize = 1,
            name = "client_address_id_sequence",
            sequenceName = "client_address_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "client_address_id_sequence")
    @Column(name = "CLIENT_ADDRESS_ID")
    private Integer id;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "CITY")
    private String city;

    @Column(name = "REGION")
    private String region;

    @Column(name = "STREET")
    private String street;

    @Column(name = "ZIP_CODE")
    @Pattern(regexp = "\\d{6}")
    private String zipCode;

    @OneToOne(cascade = {CascadeType.PERSIST ,CascadeType.REFRESH},
            mappedBy = "clientAddress",
            fetch = FetchType.EAGER)
    @JsonBackReference
    private Client client;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ClientAddress that = (ClientAddress) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
