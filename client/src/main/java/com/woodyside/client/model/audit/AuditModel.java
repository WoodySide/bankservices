package com.woodyside.client.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AuditModel {

    private static final long serialVersionUUID = 1L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registered_at", updatable = false)
    @CreatedDate
    @JsonIgnore
    private Date registeredAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    @LastModifiedDate
    @JsonIgnore
    private Date updatedAt;
}
