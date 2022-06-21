package com.woodyside.captcha.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "CaptchaEntity")
@Table(name = "captcha", schema = "captcha_schema")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
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
}
