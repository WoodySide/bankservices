package com.woodyside.captcha.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Entity(name = "CaptchaEntity")
@Table(name = "captcha", schema = "captcha_schema")
@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class CaptchaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "captcha_id")
    private String captchaId;

    @Column(name = "entered_text")
    private String enteredText;

    @Column(name = "code_hash")
    private String codeHash;

    @Column(name = "validated_result", columnDefinition = "boolean default false")
    private Boolean validatedResult;

    @Column(name = "is_used", columnDefinition = "boolean default false")
    private Boolean isUsed;

    @Column(name = "client_username")
    private String clientUsername;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CaptchaEntity that = (CaptchaEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
