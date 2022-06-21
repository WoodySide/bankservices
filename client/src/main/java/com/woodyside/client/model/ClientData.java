package com.woodyside.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientData {

    @Size(min = 10, max = 25, message = "Client's name can not be longer than 25 symbols")

    private String firstName;

    @Size(min = 20, max = 40, message = "Client's last name can not be longer than 25 symbols")
    private String lastName;

    @Email(message = "Email should be valid")
    @NaturalId
    @Size(min = 30, max = 50,
            message = "Client's email; can not be longer than 25 symbols")
    @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
            message = "Email should stick to the pattern")
    private String email;

    @Pattern(regexp = "(^8|7|\\+7)((\\d{10})|(\\s\\(\\d{3}\\)\\s\\d{3}\\s\\d{2}\\s\\d{2}))\n",
            message = "Phone number should stick to the pattern")
    private String phone;
}
